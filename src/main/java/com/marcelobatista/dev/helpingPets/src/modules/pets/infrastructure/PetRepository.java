package com.marcelobatista.dev.helpingPets.src.modules.pets.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
  @Query(value = "SELECT * FROM pets WHERE pet_status = :status", nativeQuery = true)
  Page<PetEntity> findByPetStatus(@Param("status") String status, Pageable pageable);

  Page<PetEntity> findByOwnerId(Long ownerId, Pageable pageable);

  Page<PetEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

  // TODO: FOR TESTING PURPOSES
  @Query(value = "SELECT * FROM pets p WHERE p.name = :name ORDER BY p.noExiste DESC", nativeQuery = true)
  List<PetEntity> findByName(@Param("name") String name);
}
