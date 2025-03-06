package com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;

public interface FoundPetRepository extends JpaRepository<FoundPetReport, Long> {

}
