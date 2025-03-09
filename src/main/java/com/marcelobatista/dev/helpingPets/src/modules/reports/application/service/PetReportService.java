package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.PetReportDTO;

public interface PetReportService {
  Page<PetReportDTO> getAllReports(Pageable pageable);
}
