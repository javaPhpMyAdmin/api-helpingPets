package com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

@Repository
public interface LostPetReportRepository extends JpaRepository<LostPetReport, Long> {
  List<LostPetReport> findByStatus(ReportStatus status);

  List<LostPetReport> findByReporter_Id(Long reporterId);

}
