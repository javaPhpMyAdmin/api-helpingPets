package com.marcelobatista.dev.helpingPets.src.modules.pets.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.PetStatus;

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

  private String name;
  private String breed;
  private String description;
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  @Setter
  private PetStatus petStatus;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  public void assignOwner(User owner) {
    if (this.owner == null) { // Solo permitir asignación una vez
      this.owner = owner;
    } else {
      throw new IllegalStateException("Owner cannot be changed once set");
    }
  }

}
