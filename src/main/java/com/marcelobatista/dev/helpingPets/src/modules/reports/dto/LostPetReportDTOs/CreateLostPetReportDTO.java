package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

import jakarta.validation.constraints.NotBlank;
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
public class CreateLostPetReportDTO {
  @NotBlank(message = "Name cannot be empty")
  private String petName;

  @NotBlank(message = "Breed cannot be empty")
  private String breed;

  @NotBlank(message = "Description cannot be empty")
  private String description;

  @NotBlank(message = "Image cannot be empty")
  private String imageUrl;

  @NotBlank(message = "Contact email cannot be empty")
  private String contactEmail;
}
