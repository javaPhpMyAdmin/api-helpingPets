package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import java.time.LocalDateTime;

import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

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
public class MetadataDTO {
  private ReportStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
