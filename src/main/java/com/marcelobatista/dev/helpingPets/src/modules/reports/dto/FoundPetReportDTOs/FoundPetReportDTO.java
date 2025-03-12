package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// public class FoundPetReportDTO {
// private Long id;
// private ReportType reportType;
// private String title;
// private Long reporterId;
// private String imageUrl;
// private String description;
// private Double latitude;
// private Double longitude;
// private ReportStatus status;
// private LocalDateTime reportedAt;
// private LocalDateTime updatedAt;
// }

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoundPetReportDTO {
  private Long reportId;
  private ReportType reportType;
  private ReporterDTO reporter;
  private ImageDTO image;
  private LocationDTO location;
  private MetadataDTO metadata;
}
