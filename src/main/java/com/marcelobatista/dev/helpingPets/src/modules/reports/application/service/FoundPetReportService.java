package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;

public interface FoundPetReportService {
  FoundPetReportDTO createReport(FoundPetReportDTO foundPetReportDTO);

  List<FoundPetReportDTO> getAllReports();

  FoundPetReportDTO getReportById(Long foundPetId);

  FoundPetReportDTO updateReport(UpdateFoundPetReportDTO updateFoundPetReportDTO);

  void deleteReport(Long foundPetId);
}
