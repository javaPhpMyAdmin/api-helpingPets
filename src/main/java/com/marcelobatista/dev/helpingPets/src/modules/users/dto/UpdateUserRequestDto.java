package com.marcelobatista.dev.helpingPets.src.modules.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequestDto {
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;

  @NotBlank
  private String profileImageUrl;
}
