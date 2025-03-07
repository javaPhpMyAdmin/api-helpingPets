package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure.LostPetReportRepository;
import com.marcelobatista.dev.helpingPets.src.modules.reports.mapper.LostPetReportMapper;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostPetReportServiceImpl implements LostPetReportService {
  private final LostPetReportRepository lostPetReportRepository;
  private final LostPetReportMapper lostPetReportMapper;

  @Override
  @Transactional
  public LostPetReportDTO createReport(LostPetReportDTO lostPetReportDTO) {
    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder().status(401).message("Unauthorized: user not authenticated").build());
    var lostPetReport = lostPetReportMapper.toEntity(lostPetReportDTO);
    lostPetReport.setReporter(currentUser);
    lostPetReport.setStatus(lostPetReportDTO.getStatus());
    return lostPetReportMapper.toDto(lostPetReportRepository.save(lostPetReport));
  }

  @Override
  @Transactional(readOnly = true)
  public List<LostPetReportDTO> getAllReports() {
    return lostPetReportMapper.toDtoList(lostPetReportRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public LostPetReportDTO getReportById(Long lostPetId) {
    if (lostPetId == null) {
      throw ApiException.builder().status(400).message("Bad Request: lostPetId is required").build();
    }
    return lostPetReportRepository.findById(lostPetId)
        .map(lostPetReportMapper::toDto)
        .orElseThrow(() -> ApiException.builder().status(404).message("LostPetReport not found").build());
  }

  @Override
  @Transactional
  public LostPetReportDTO updateReport(LostPetReportDTO lostPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'updateReport'");
  }

  @Override
  @Transactional
  public void deleteReport(Long lostPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteReport'");
  }

}
