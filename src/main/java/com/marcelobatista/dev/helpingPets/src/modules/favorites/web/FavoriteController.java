
package com.marcelobatista.dev.helpingPets.src.modules.favorites.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.modules.favorites.application.FavoritesService;
import com.marcelobatista.dev.helpingPets.src.modules.favorites.dto.FavoriteDTO;
import com.marcelobatista.dev.helpingPets.src.shared.Response.GlobalResponse;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {
  private final FavoritesService favoritesService;
  private final RequestUtils requestUtils;

  @PostMapping()
  public ResponseEntity<GlobalResponse> addFavorite(
      @RequestBody FavoriteDTO favoriteDTO,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("result", favoritesService.addFavorite(favoriteDTO)),
            "Favorite Added Successfully ", HttpStatus.CREATED));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<GlobalResponse> getUserFavorites(@PathVariable(name = "userId") @Valid Long userId,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request,
            Map.of("result", favoritesService.getUserFavorites(userId)),
            "Favorites Retrieved Successfully ", HttpStatus.OK));
  }

  @DeleteMapping()
  public ResponseEntity<GlobalResponse> removeFavorite(@RequestBody @Valid FavoriteDTO favoriteDTO,
      HttpServletRequest request) {
    favoritesService.removeFavorite(favoriteDTO);
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request,
            Map.of("result", String.format("Deleted fav for userId %s ", favoriteDTO.getUserId())),
            "Favorite Deleted Successfully ", HttpStatus.NO_CONTENT));
  }

}
