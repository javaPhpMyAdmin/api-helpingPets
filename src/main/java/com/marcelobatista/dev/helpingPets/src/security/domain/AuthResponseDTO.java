package com.marcelobatista.dev.helpingPets.src.security.domain;

import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UserResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDTO {
  private String accessToken;
  private String refreshToken;
  private UserResponse user; // Devuelve datos básicos del usuario
}
