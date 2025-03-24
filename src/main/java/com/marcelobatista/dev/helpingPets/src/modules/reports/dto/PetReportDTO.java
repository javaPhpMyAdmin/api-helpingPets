package com.marcelobatista.dev.helpingPets.src.modules.reports.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetReportDTO {
  private Long id;
  private String reportType; // "LOST" o "FOUND"
  private String title;
  private String breed;
  private String description;
  private List<String> imagesLostPet;
  private String imageFoundPet;
  private Double latitude;
  private Double longitude;
  private String contactEmail;
  private String petName;
  private String status; // Estado del reporte
  private String reportedBy; // Nombre o email del usuario
  private LocalDateTime reportedAt;
  private LocalDateTime updatedAt;
}
