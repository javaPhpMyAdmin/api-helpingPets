package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTO;

@Service
public class FoundPetReportServiceImpl implements FoundPetReportService {

  @Override
  public FoundPetReportDTO createReport(FoundPetReportDTO foundPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'createReport'");
  }

  @Override
  public List<FoundPetReportDTO> getAllReports() {
    throw new UnsupportedOperationException("Unimplemented method 'getAllReports'");
  }

  @Override
  public FoundPetReportDTO getReportById(Long foundPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'getReportById'");
  }

  @Override
  public FoundPetReportDTO updateReport(FoundPetReportDTO FoundPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'updateReport'");
  }

  @Override
  public void deleteReport(Long foundPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteReport'");
  }

}
