package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTO;

public interface LostPetReportService {
  LostPetReportDTO createReport(LostPetReportDTO lostPetReportDTO);

  List<LostPetReportDTO> getAllReports();

  LostPetReportDTO getReportById(Long lostPetId);

  LostPetReportDTO updateReport(LostPetReportDTO lostPetReportDTO);

  void deleteReport(Long lostPetId);
}
