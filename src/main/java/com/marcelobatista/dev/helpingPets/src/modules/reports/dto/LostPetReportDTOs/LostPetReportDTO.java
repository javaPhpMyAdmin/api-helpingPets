package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

import java.time.LocalDateTime;

import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

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
public class LostPetReportDTO {
  private Long id;
  private String petName;
  private String breed;
  private String description;
  private String imageUrl;
  private LocalDateTime reportedAt;
  private ReportStatus status;
  private String contactEmail;
  private Long reporterId;
}
