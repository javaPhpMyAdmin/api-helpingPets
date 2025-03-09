package com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLostPetReportDTO {
  @NotBlank(message = "Name cannot be empty")
  private String petName;

  @NotBlank(message = "Breed cannot be empty")
  private String breed;

  @NotBlank(message = "Description cannot be empty")
  @Size(max = 500, message = "Description cannot be longer than 500 characters")
  private String description;

  @NotEmpty(message = "At least one image is required")
  @Size(min = 1, message = "At least one image URL must be provided")
  private List<MultipartFile> imageUrls;

  @NotBlank(message = "Contact email cannot be empty")
  private String contactEmail;
}
