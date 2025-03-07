package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

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
public class UpdateLostPetReportDTO {
  private Long lostPetReportId;
  private String petName;
  private String breed;
  private String description;
  private String imageUrl;
  private String status;
}
