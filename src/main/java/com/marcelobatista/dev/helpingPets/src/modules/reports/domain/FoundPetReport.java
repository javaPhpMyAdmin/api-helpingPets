package com.marcelobatista.dev.helpingPets.src.modules.reports.domain;

import java.time.LocalDateTime;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "found_pet_reports")
public class FoundPetReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "reporter_id", nullable = false)
  private User reporter;

  private String imageUrl;
  private String description;

  private Double latitude;
  private Double longitude;

  @Enumerated(EnumType.STRING)
  private ReportStatus status = ReportStatus.PENDING;

  private LocalDateTime createdAt = LocalDateTime.now();

}
