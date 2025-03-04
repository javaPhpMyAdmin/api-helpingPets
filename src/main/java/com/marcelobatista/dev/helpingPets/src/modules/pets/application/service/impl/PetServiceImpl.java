package com.marcelobatista.dev.helpingPets.src.modules.pets.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.pets.application.service.PetService;
import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.infrastructure.PetRepository;
import com.marcelobatista.dev.helpingPets.src.modules.pets.mapper.PetMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

  private final PetMapper petMapper;
  private final PetRepository petRepository;
  private final UserRepository userRepository;

  @Override
  public PetDTO createPet(PetDTO petDTO) {
    User owner = userRepository.findById(petDTO.getOwnerId())
        .orElseThrow(() -> new RuntimeException("Owner not found"));
    PetEntity pet = petMapper.toEntity(petDTO);
    pet.assignOwner(owner);
    pet.setPetStatus(PetStatus.AVAILABLE);
    return petMapper.toDto(petRepository.save(pet));
  }

  @Override
  public List<PetDTO> getAllPets() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllPets'");
  }

  @Override
  public List<PetDTO> getPetsByStatus(PetStatus status) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPetsByStatus'");
  }

  @Override
  public List<PetDTO> getPetsByOwner(Long ownerId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPetsByOwner'");
  }

  @Override
  public void deletePet(Long petId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deletePet'");
  }

}
