package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record CreateFoundPetReportDTO(String title,

    ImageDTO imageDTO,

    // @NotBlank(message = "Description is required")
    // @Size(max = 500, message = "Description cannot be longer than 500
    // characters")
    // private String description;

    // @NotNull(message = "Latitude is required")
    // private Double latitude;

    // @NotNull(message = "Latitude is required")
    // private Double longitude;

    LocationDTO location,

    String foundPetStatus

) {
}
