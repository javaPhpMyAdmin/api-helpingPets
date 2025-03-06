package com.marcelobatista.dev.helpingPets.src.modules.reports.dto;

import java.time.LocalDateTime;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoundPetReportDTO {
  private Long id;
  private User reporter;

  private String imageUrl;
  private String description;

  private Double latitude;
  private Double longitude;

  private ReportStatus status;

  private LocalDateTime createdAt;
}
