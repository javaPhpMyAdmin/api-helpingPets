package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

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
public class CreateFoundPetReportDTO {
  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Image is required")
  private String imageUrl;

  @NotBlank(message = "Description is required")
  private String description;

  @NotBlank(message = "Latitude is required")
  private Double latitude;

  @NotBlank(message = "Latitude is required")
  private Double longitude;

  @NotBlank(message = "Status is required")
  private String foundPetStatus;

}
