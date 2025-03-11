package com.marcelobatista.dev.helpingPets.src.modules.favorites.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

import jakarta.persistence.Entity;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "favorites")
public class FavoriteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "pet_id", nullable = false)
  private PetEntity pet;

  @CreatedDate
  private LocalDateTime createdAt;
}
