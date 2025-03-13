package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

public record CreateFoundPetReportDTO(String title, CreateImageDTO imageDTO, LocationDTO location,
    String foundPetStatus) {
}
