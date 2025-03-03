package com.marcelobatista.dev.helpingPets.src.modules.pets.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
  List<PetEntity> findByStatus(PetStatus status);

  List<PetEntity> findByOwnerId(Long ownerId);
}
