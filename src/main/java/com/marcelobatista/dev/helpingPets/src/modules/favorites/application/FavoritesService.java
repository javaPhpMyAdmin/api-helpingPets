package com.marcelobatista.dev.helpingPets.src.modules.favorites.application;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.favorites.dto.FavoriteDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;

public interface FavoritesService {
  FavoriteDTO addFavorite(FavoriteDTO favoriteDTO);

  void removeFavorite(FavoriteDTO favoriteDTO);

  List<PetDTO> getUserFavorites(Long userId);
}
