package com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;

@Repository
public interface LostPetReportRepository extends JpaRepository<LostPetReport, Long> {

}
