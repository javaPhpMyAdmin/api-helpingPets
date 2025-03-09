package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;

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
  private ReportType reportType;
  private Long reporterId;
  private List<String> imageUrls;
  private String description;
  private String contactEmail;
  private String petName;
  private String breed;
  private ReportStatus status;
  private LocalDateTime reportedAt;
  private LocalDateTime updatedAt;
}
