package com.marcelobatista.dev.helpingPets.src.modules.pets.application.service.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.pets.application.service.PetService;
import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetCreateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetUpdateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.infrastructure.PetRepository;
import com.marcelobatista.dev.helpingPets.src.modules.pets.mapper.PetMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.ImageService.UploadImage;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {

  private final PetMapper petMapper;
  private final PetRepository petRepository;
  private final UserRepository userRepository;
  private final UploadImage uploadImage;

  @Override
  @Transactional
  public PetDTO createPet(PetCreateDTO petCreateDTO) {

    PetEntity pet = petMapper.toEntity(petCreateDTO);

    User owner = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("unauthorized: user not authenticated").build());

    pet.assignOwner(owner);
    pet.setPetStatus(PetStatus.valueOf(petCreateDTO.getStatus()));

    if (petCreateDTO.getImageUrls() != null && !petCreateDTO.getImageUrls().isEmpty()) {
      List<String> imageUrls = petCreateDTO.getImageUrls().stream()
          .map(t -> {
            try {
              return uploadImage.uploadImageToCloudinary(t, ReportType.ADOPTION);
            } catch (IOException e) {
              throw new UncheckedIOException("Failed to upload image to Cloudinary", e);
            }
          }).collect(Collectors.toList());
      pet.setImageUrls(imageUrls);
    }

    PetEntity savedPet = Optional.of(petRepository.save(pet))
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Failed to save pet").build());

    return petMapper.toDto(savedPet);
  }

  @Override
  @Transactional
  public PetDTO updatePet(PetUpdateDTO petUpdateDTO) {

    if (petUpdateDTO.getPetId() == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Pet ID must not be null")
          .build();
    }

    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("User must be authenticated")
            .build());

    var petEntity = petRepository.findById(petUpdateDTO.getPetId())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Pet not found")
            .build());

    if (!petEntity.getOwner().getId().equals(currentUser.getId())) {
      throw ApiException.builder()
          .status(HttpStatus.FORBIDDEN.value())
          .message("You are not the owner of this pet")
          .build();
    }
    petEntity.updateFromDto(petUpdateDTO);

    return petMapper.toDto(petEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PetDTO> getAllPets(Pageable pageable) {
    Page<PetEntity> pets = petRepository.findAllByOrderByCreatedAtDesc(pageable);
    return pets.map(petMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PetDTO> getPetsByStatus(String status, Pageable pageable) {
    PetStatus petStatus = PetStatus.valueOf(status.toUpperCase());
    Page<PetEntity> pets = petRepository.findByPetStatus(petStatus.name(), pageable);
    return pets.map(petMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PetDTO> getPetsByOwner(Long ownerId, Pageable pageable) {

    Objects.requireNonNull(ownerId, "Owner ID must not be null");

    var owner = userRepository.findById(ownerId)
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Owner not found")
            .build());

    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("User must be authenticated")
            .build());

    if (!owner.getId().equals(currentUser.getId())) {
      throw ApiException.builder()
          .status(HttpStatus.FORBIDDEN.value())
          .message("You are not the owner of this pet")
          .build();
    }

    Page<PetEntity> pets = petRepository.findByOwnerId(currentUser.getId(), pageable);

    return pets.map(petMapper::toDto);
  }

  @Override
  @Transactional
  public void deletePet(Long petId) {
    if (petId == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Pet ID must not be null")
          .build();
    }

    // Obtener el usuario autenticado
    User currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized: User not authenticated")
            .build());

    // Buscar la mascota
    PetEntity existingPet = petRepository.findById(petId)
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Pet not found")
            .build());

    // Verificar si el usuario autenticado es el dueño de la mascota
    if (!existingPet.getOwner().getId().equals(currentUser.getId())) {
      throw ApiException.builder()
          .status(HttpStatus.FORBIDDEN.value())
          .message("You are not the owner of this pet")
          .build();
    }

    petRepository.delete(existingPet);
    // Log de auditoría
    log.info("Pet with ID {} deleted by user {}", petId, currentUser.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public PetDTO getPetById(Long petId) {
    if (petId == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Pet ID must not be null")
          .build();
    }

    return petRepository.findById(petId)
        .map(petMapper::toDto)
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Pet not found")
            .build());
  }

}
