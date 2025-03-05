package com.marcelobatista.dev.helpingPets.src.modules.pets.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetCreateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetUpdateDTO;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;

public interface PetService {
  PetDTO createPet(PetCreateDTO petCreateDTO);

  PetDTO updatePet(PetUpdateDTO petUpdateDTO);

  List<PetDTO> getAllPets();

  List<PetDTO> getPetsByStatus(PetStatus status);

  PetDTO getPetById(Long petId);

  List<PetDTO> getPetsByOwner(Long ownerId);

  void deletePet(Long petId);

}
