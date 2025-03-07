package com.marcelobatista.dev.helpingPets.src.modules.reports.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "found_pet_reports")
public class FoundPetReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String title;

  @ManyToOne
  @JoinColumn(name = "reporter_id", nullable = false)
  private User reporter;

  @Setter
  private String imageUrl;
  @Setter
  private String description;
  @Setter
  private Double latitude;
  @Setter
  private Double longitude;

  @Enumerated(EnumType.STRING)
  @Setter
  private ReportStatus status = ReportStatus.PENDING;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @PrePersist
  private void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  private void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public void updateFromDto(UpdateFoundPetReportDTO updateFoundPetReportDTO) {
    Optional.of(updateFoundPetReportDTO.getTitle()).ifPresent(this::setTitle);
    Optional.of(updateFoundPetReportDTO.getImageUrl()).ifPresent(this::setImageUrl);
    Optional.of(updateFoundPetReportDTO.getDescription()).ifPresent(this::setDescription);
    Optional.of(updateFoundPetReportDTO.getLatitude()).ifPresent(this::setLatitude);
    Optional.of(updateFoundPetReportDTO.getLongitude()).ifPresent(this::setLongitude);
    Optional.ofNullable(updateFoundPetReportDTO.getFoundPetStatus()).ifPresent(status -> {
      try {
        this.setStatus(ReportStatus.valueOf(status.toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw ApiException.builder().message(("Invalid status value: " + status)).build();
      }
    });
  }

}
