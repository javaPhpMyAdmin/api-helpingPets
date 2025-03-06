package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTO;

@Service
public class LostPetReportServiceImpl implements LostPetReportService {

  @Override
  public LostPetReportDTO createReport(LostPetReportDTO lostPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'createReport'");
  }

  @Override
  public List<LostPetReportDTO> getAllReports() {
    throw new UnsupportedOperationException("Unimplemented method 'getAllReports'");
  }

  @Override
  public LostPetReportDTO getReportById(Long lostPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'getReportById'");
  }

  @Override
  public LostPetReportDTO updateReport(LostPetReportDTO lostPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'updateReport'");
  }

  @Override
  public void deleteReport(Long lostPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteReport'");
  }

}
