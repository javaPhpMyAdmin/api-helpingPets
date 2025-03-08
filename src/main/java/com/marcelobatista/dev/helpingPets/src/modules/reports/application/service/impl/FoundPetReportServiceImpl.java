package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure.FoundPetRepository;
import com.marcelobatista.dev.helpingPets.src.modules.reports.mapper.FoundPetReportMapper;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundPetReportServiceImpl implements FoundPetReportService {
  private final FoundPetRepository foundPetRepository;
  private final FoundPetReportMapper foundPetReportMapper;

  @Override
  @Transactional
  public FoundPetReportDTO createReport(CreateFoundPetReportDTO createFoundPetReportDTO) {
    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized: user not authenticated").build());

    FoundPetReport foundPetReport = foundPetReportMapper.toEntity(createFoundPetReportDTO);
    foundPetReport.setReporter(currentUser);
    foundPetReport.setStatus(ReportStatus.OPEN);

    return foundPetReportMapper.toDto(
        Optional.of(foundPetRepository.save(foundPetReport))
            .orElseThrow(() -> ApiException.builder().status(500).message("Failed to save lostPetReport").build()));

  }

  @Override
  @Transactional(readOnly = true)
  public List<FoundPetReportDTO> getAllReports() {
    List<FoundPetReport> reports = foundPetRepository.findAll();

    if (reports.isEmpty()) {
      return Collections.emptyList();
    }

    return foundPetReportMapper.toDtoList(reports);
  }

  @Override
  @Transactional(readOnly = true)
  public FoundPetReportDTO getReportById(Long foundPetId) {
    if (foundPetId == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Lost Pet ID must not be null")
          .build();
    }

    return foundPetRepository.findById(foundPetId)
        .map(foundPetReportMapper::toDto)
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.BAD_REQUEST.value())
            .message("LostPetReport not found").build());
  }

  @Override
  @Transactional
  public FoundPetReportDTO updateReport(UpdateFoundPetReportDTO updateFoundPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'updateReport'");
  }

  @Override
  @Transactional
  public void deleteReport(Long foundPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteReport'");
  }

}
