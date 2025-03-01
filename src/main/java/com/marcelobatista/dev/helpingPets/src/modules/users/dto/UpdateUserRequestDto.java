package com.marcelobatista.dev.helpingPets.src.modules.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
}
