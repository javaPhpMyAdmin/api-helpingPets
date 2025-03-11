package com.marcelobatista.dev.helpingPets.src.modules.favorites.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {
  private Long userId;
  private Long petId;
}
