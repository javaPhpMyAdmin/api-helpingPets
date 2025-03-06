package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTO;

public interface FoundPetReportService {
  FoundPetReportDTO createReport(FoundPetReportDTO foundPetReportDTO);

  List<FoundPetReportDTO> getAllReports();

  FoundPetReportDTO getReportById(Long foundPetId);

  FoundPetReportDTO updateReport(FoundPetReportDTO FoundPetReportDTO);

  void deleteReport(Long foundPetId);
}
