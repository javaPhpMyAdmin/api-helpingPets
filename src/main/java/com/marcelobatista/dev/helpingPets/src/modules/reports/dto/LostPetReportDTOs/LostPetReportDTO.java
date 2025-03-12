package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

import java.util.List;

public record LostPetReportDTO(Long reportId, String reportType, LostReporterDTO reporter, List<LostImageDTO> images,
    LostPetDTO pet, LostMetadataDTO metadata) {

  // private Long id;
  // private ReportType reportType;
  // private Long reporterId;
  // private List<String> imageUrls;
  // private String description;
  // private String contactEmail;
  // private String petName;
  // private String breed;
  // private ReportStatus status;
  // private LocalDateTime reportedAt;
  // private LocalDateTime updatedAt;
}
