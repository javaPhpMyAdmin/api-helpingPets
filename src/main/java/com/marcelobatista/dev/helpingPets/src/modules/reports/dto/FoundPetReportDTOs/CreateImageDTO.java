package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import org.springframework.web.multipart.MultipartFile;

public record CreateImageDTO(MultipartFile image, String description) {

}
