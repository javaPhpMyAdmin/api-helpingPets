package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

import java.time.LocalDateTime;

import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

public record LostMetadataDTO(LocalDateTime reportedAt, LocalDateTime updatedAt, ReportStatus status) {

}
