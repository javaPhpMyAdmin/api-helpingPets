package com.marcelobatista.dev.helpingPets.src.modules.favorites.application.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.favorites.application.FavoritesService;
import com.marcelobatista.dev.helpingPets.src.modules.favorites.domain.FavoriteEntity;
import com.marcelobatista.dev.helpingPets.src.modules.favorites.dto.FavoriteDTO;
import com.marcelobatista.dev.helpingPets.src.modules.favorites.infrastructure.FavoriteRepository;
import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.infrastructure.PetRepository;
import com.marcelobatista.dev.helpingPets.src.modules.pets.mapper.PetMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {

  private final PetRepository petRepository;
  private final FavoriteRepository favoriteRepository;
  private final PetMapper petMapper;

  @Override
  @Transactional
  public FavoriteDTO addFavorite(FavoriteDTO favoriteDTO) {
    User currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser()).orElseThrow(
        () -> ApiException.builder().status(HttpStatus.UNAUTHORIZED.value()).message("User not authenticated").build());

    PetEntity petEntity = petRepository.findById(favoriteDTO.getPetId())
        .orElseThrow(
            () -> ApiException.builder().status(HttpStatus.NOT_FOUND.value()).message("Pet not found").build());

    if (favoriteDTO.getUserId() != currentUser.getId()) {
      throw ApiException.builder().status(HttpStatus.FORBIDDEN.value()).message("User not allowed to add favorite")
          .build();
    }

    // Verificar si ya está en favoritos
    if (favoriteRepository.findByUserIdAndPetId(favoriteDTO.getUserId(), favoriteDTO.getPetId()).isPresent()) {
      throw ApiException.builder().message("This pet is already in favorites").status(HttpStatus.CONFLICT.value())
          .build();
    }

    FavoriteEntity favoriteEntity = FavoriteEntity.builder().user(
        currentUser).pet(petEntity).build();

    favoriteRepository.save(favoriteEntity);

    return FavoriteDTO.builder().userId(favoriteDTO.getUserId()).petId(favoriteDTO.getPetId()).build();
  }

  @Override
  @Transactional(readOnly = true)
  public List<PetDTO> getUserFavorites(Long userId) {
    return favoriteRepository.findByUserId(userId).stream()
        .map(fav -> petMapper.toDto(fav.getPet()))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void removeFavorite(FavoriteDTO favoriteDTO) {
    Optional<FavoriteEntity> favorite = favoriteRepository.findByUserIdAndPetId(favoriteDTO.getUserId(),
        favoriteDTO.getPetId());
    if (favorite.isPresent()) {
      favoriteRepository.deleteByUserIdAndPetId(favoriteDTO.getUserId(),
          favoriteDTO.getPetId());
    } else {
      throw ApiException.builder().message("Favorite does not exist").status(HttpStatus.NOT_FOUND.value()).build();
    }
  }

}
