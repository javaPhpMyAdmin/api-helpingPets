package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.UpdateLostPetReportDTO;

public interface LostPetReportService {
  LostPetReportDTO createReport(CreateLostPetReportDTO createLostPetReportDTO);

  Page<LostPetReportDTO> getAllReports(Pageable pageable);

  LostPetReportDTO getReportById(Long lostPetId);

  LostPetReportDTO updateReport(UpdateLostPetReportDTO updateLostPetReportDTO);

  void deleteReport(Long lostPetId);
}
