package com.marcelobatista.dev.helpingPets.src.modules.pets.dto;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetCreateDTO {
  @NotBlank(message = "Name cannot be empty")
  private String name;

  @NotBlank(message = "Breed cannot be empty")
  private String breed;

  @Size(max = 500, message = "Description cannot be longer than 500 characters")
  private String description;

  @NotEmpty(message = "At least one image is required")
  @Size(min = 1, message = "At least one image URL must be provided")
  private List<MultipartFile> imageUrls;

  @NotBlank(message = "Status cannot be empty")
  private String status;

  @NotBlank(message = "Gender cannot be empty")
  private String gender;

  @NotBlank(message = "Age cannot be empty")
  private String age;

  @NotBlank(message = "Weight cannot be empty")
  private Integer weight;
}
