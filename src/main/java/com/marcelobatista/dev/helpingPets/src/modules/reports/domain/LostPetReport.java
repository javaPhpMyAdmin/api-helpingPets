package com.marcelobatista.dev.helpingPets.src.modules.reports.domain;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;

import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
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
import jakarta.persistence.PrePersist;
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
  @JoinColumn(name = "pet_id", nullable = true)
  private PetEntity pet;

  @ManyToOne
  @JoinColumn(name = "reporter_id", nullable = false)
  @Setter
  private User reporter;

  private String petName;
  private String breed;
  private String description;
  private String imageUrl;
  private String contactEmail;

  @Enumerated(EnumType.STRING)
  @Setter
  private ReportStatus status = ReportStatus.OPEN;
  private LocalDateTime createdAt = LocalDateTime.now();

  @PrePersist
  private void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

}
