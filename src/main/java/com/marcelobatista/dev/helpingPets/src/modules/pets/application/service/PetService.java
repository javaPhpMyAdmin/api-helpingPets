package com.marcelobatista.dev.helpingPets.src.modules.pets.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;

public interface PetService {
  PetDTO createPet(PetDTO petDTO);

  List<PetDTO> getAllPets();

  List<PetDTO> getPetsByStatus(PetStatus status);

  List<PetDTO> getPetsByOwner(Long ownerId);

  void deletePet(Long petId);

}
