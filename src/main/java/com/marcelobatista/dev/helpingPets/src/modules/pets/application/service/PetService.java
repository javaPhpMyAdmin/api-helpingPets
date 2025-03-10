package com.marcelobatista.dev.helpingPets.src.modules.pets.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetCreateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetUpdateDTO;

public interface PetService {
  PetDTO createPet(PetCreateDTO petCreateDTO);

  PetDTO updatePet(PetUpdateDTO petUpdateDTO);

  Page<PetDTO> getAllPets(Pageable pageable);

  Page<PetDTO> getPetsByStatus(String status, Pageable pageable);

  PetDTO getPetById(Long petId);

  Page<PetDTO> getPetsByOwner(Long ownerId, Pageable pageable);

  void deletePet(Long petId);

}
