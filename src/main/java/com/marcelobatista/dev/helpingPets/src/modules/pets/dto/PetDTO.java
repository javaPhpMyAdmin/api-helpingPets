package com.marcelobatista.dev.helpingPets.src.modules.pets.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
  private Long id;
  private String name;
  private String breed;

  private String description;
  private List<String> imageUrls;

  private String status;

  private String createdAt;
  private Long ownerId;

}
