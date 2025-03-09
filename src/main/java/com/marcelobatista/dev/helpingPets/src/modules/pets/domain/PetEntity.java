package com.marcelobatista.dev.helpingPets.src.modules.pets.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetUpdateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
@Builder
public class PetEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String name;
  @Setter
  private String breed;
  @Setter
  private String description;

  @ElementCollection
  @CollectionTable(name = "pet_images", joinColumns = @JoinColumn(name = "pet_id"))
  @Column(name = "image_url")
  @Setter
  @Builder.Default
  private List<String> imageUrls = new ArrayList<>(); // Lista de URLs de imágenes

  @Enumerated(EnumType.STRING)
  @Setter
  private PetStatus petStatus;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @CreatedDate
  @Setter
  private LocalDateTime createdAt;
  @LastModifiedDate
  @Setter
  private LocalDateTime updatedAt;

  public void assignOwner(User owner) {
    Optional.ofNullable(this.owner)
        .ifPresent(o -> {
          throw ApiException.builder().message("Owner cannot be changed once set").build();
        });

    this.owner = owner;
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public void updateFromDto(PetUpdateDTO petUpdateDTO) {
    Optional.ofNullable(petUpdateDTO.getName()).ifPresent(this::setName);
    Optional.ofNullable(petUpdateDTO.getBreed()).ifPresent(this::setBreed);
    Optional.ofNullable(petUpdateDTO.getDescription()).ifPresent(this::setDescription);
    Optional.ofNullable(petUpdateDTO.getImageUrls()).ifPresent(this::setImageUrls);

    Optional.ofNullable(petUpdateDTO.getStatus()).ifPresent(status -> {
      try {
        this.setPetStatus(PetStatus.valueOf(status.toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw ApiException.builder().message(("Invalid status value: " + status)).build();
      }
    });
  }

}
