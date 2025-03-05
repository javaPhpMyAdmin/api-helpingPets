package com.marcelobatista.dev.helpingPets.src.modules.pets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetCreateDTO {
  @NotBlank(message = "Name cannot be empty")
  private String name;

  @NotBlank(message = "Breed cannot be empty")
  private String breed;

  @Size(max = 500, message = "Description cannot be longer than 500 characters")
  private String description;

  @NotBlank(message = "Image URL cannot be empty")
  private String imageUrl;

  @NotBlank(message = "Status cannot be empty")
  private String status;
}
