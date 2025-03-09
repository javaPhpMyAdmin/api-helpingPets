package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;

public interface FoundPetReportService {
  FoundPetReportDTO createReport(CreateFoundPetReportDTO createFoundPetReportDTO);

  Page<FoundPetReportDTO> getAllReports(Pageable pageable);

  FoundPetReportDTO getReportById(Long foundPetId);

  FoundPetReportDTO updateReport(UpdateFoundPetReportDTO updateFoundPetReportDTO);

  void deleteReport(Long foundPetId);
}
