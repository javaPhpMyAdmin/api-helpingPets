package com.marcelobatista.dev.helpingPets.src.entities.users.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
}
