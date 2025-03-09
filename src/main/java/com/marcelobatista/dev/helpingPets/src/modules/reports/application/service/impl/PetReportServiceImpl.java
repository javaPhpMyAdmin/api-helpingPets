package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.PetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.PetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.mapper.PetReportMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetReportServiceImpl implements PetReportService {
  private final LostPetReportService lostPetReportService;
  private final FoundPetReportService foundPetReportService;
  private final PetReportMapper petReportMapper;

  @Override
  public Page<PetReportDTO> getAllReports(Pageable pageable) {
    // Obtén los informes de mascotas perdidas y encontradas como páginas
    Page<LostPetReportDTO> lostPetsPage = lostPetReportService.getAllReports(pageable);
    Page<FoundPetReportDTO> foundPetsPage = foundPetReportService.getAllReports(pageable);

    // Convierte las páginas a listas de PetReportDTO
    List<PetReportDTO> lostPetsDTO = lostPetsPage.getContent().stream()
        .map(petReportMapper::toPetReportDTO)
        .collect(Collectors.toList());

    List<PetReportDTO> foundPetsDTO = foundPetsPage.getContent().stream()
        .map(petReportMapper::toPetReportDTO)
        .collect(Collectors.toList());

    // Combina ambas listas
    List<PetReportDTO> combinedReports = new ArrayList<>();
    combinedReports.addAll(lostPetsDTO);
    combinedReports.addAll(foundPetsDTO);

    // Crea una página a partir de la lista combinada
    return new PageImpl<>(combinedReports, pageable, combinedReports.size());
  }

}
