package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.UpdateLostPetReportDTO;

public interface LostPetReportService {
  LostPetReportDTO createReport(CreateLostPetReportDTO createLostPetReportDTO);

  List<LostPetReportDTO> getAllReports();

  LostPetReportDTO getReportById(Long lostPetId);

  LostPetReportDTO updateReport(UpdateLostPetReportDTO updateLostPetReportDTO);

  void deleteReport(Long lostPetId);
}
