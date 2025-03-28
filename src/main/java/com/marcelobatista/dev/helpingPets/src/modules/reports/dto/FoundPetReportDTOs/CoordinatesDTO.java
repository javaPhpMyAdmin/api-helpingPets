package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinatesDTO {
  private Double latitude;
  private Double Longitude;
}
