package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
  @Size(max = 500, message = "Description cannot be longer than 500 characters")
  private String description;

  @NotNull(message = "Latitude is required")
  private Double latitude;

  @NotNull(message = "Latitude is required")
  private Double longitude;

  @NotBlank(message = "Status is required")
  private String foundPetStatus;

}
