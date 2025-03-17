package com.marcelobatista.dev.helpingPets.src.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponseDTO {
  private String accessToken;
  private String refreshToken;
  private String message;
}
