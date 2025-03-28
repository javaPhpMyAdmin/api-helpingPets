package com.marcelobatista.dev.helpingPets.src.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequestDTO {
  @Email(message = "Invalid email address")
  @NotNull(message = "Email cannot be empty or null")
  private String email;
  private String password;
}
