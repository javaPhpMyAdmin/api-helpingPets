package com.marcelobatista.dev.helpingPets.src.modules.pets.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetUpdateDTO {

  private Long petId;
  private String name;
  private String breed;
  private String description;
  private List<String> imageUrls;
  private String status; // Puede cambiarse opcionalmente

}
