package com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

public interface FoundPetRepository extends JpaRepository<FoundPetReport, Long> {
  List<FoundPetReport> findByStatus(ReportStatus status);

  List<FoundPetReport> findByLatitudeAndLongitude(Double latitude, Double longitude);

  List<FoundPetReport> findByReporter_Id(Long reporterId);
}
