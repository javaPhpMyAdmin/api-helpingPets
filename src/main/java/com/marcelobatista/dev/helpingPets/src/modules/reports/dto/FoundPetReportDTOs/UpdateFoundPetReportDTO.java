package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoundPetReportDTO {
  private String title;
  private String imageUrl;
  private String description;
  private Double latitude;
  private Double longitude;
  private String foundPetStatus;
}
