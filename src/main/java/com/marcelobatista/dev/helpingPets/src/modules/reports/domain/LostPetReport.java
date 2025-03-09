package com.marcelobatista.dev.helpingPets.src.modules.reports.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.UpdateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "lost_pet_reports")
public class LostPetReport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "reporter_id", nullable = false)
  @Setter
  private User reporter;

  @Setter
  private String petName;
  @Setter
  private String breed;
  @Setter
  private String description;

  @ElementCollection
  @CollectionTable(name = "lost_pet_report_images", joinColumns = @JoinColumn(name = "lost_pet_report_id"))
  @Column(name = "image_url")
  @Setter
  private List<String> imageUrls = new ArrayList<>(); // Lista de URLs de imágenes

  @Setter
  private String contactEmail;

  @Enumerated(EnumType.STRING)
  @Setter
  private ReportType reportType = ReportType.LOST;

  @Enumerated(EnumType.STRING)
  @Setter
  private ReportStatus status = ReportStatus.OPEN;
  private LocalDateTime reportedAt;
  private LocalDateTime updatedAt;

  @PrePersist
  private void onCreate() {
    this.reportedAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  private void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public void updateFromDto(UpdateLostPetReportDTO dto) {
    Optional.ofNullable(dto.getPetName()).ifPresent(this::setPetName);
    Optional.ofNullable(dto.getBreed()).ifPresent(this::setBreed);
    Optional.ofNullable(dto.getDescription()).ifPresent(this::setDescription);
    Optional.ofNullable(dto.getImageUrls()).ifPresent(this::setImageUrls);
    Optional.ofNullable(dto.getStatus()).ifPresent(status -> {
      try {
        this.setStatus(ReportStatus.valueOf(status.toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw ApiException.builder().message(("Invalid status value: " + status)).build();
      }
    });
  }
}
