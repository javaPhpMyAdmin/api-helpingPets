package com.marcelobatista.dev.helpingPets.src.modules.pets.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "Pets managment")
@Slf4j
public class PetController {

  @GetMapping()
  public ResponseEntity<?> getAllPets() {
    return ResponseEntity.ok().body(Map.of("Message", "Getting all pets for adoption"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getSpecificPet(@PathVariable(name = "id") @Valid @NotNull int id) {
    return ResponseEntity.ok().body(Map.of("Message", "Getting Pet for adoption"));
  }

  @PostMapping()
  public ResponseEntity<?> addNewPet(@RequestBody @Valid String PetDTO) {
    return ResponseEntity.ok().body(Map.of("Message", "Added pet for adoption"));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePet(@PathVariable("id") int id, @RequestBody @Valid String UpdatePetDTO) {
    return ResponseEntity.ok().body(Map.of("PetId", id, "Body", UpdatePetDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePet(@PathVariable("id") @Valid @NotNull int id) {
    return ResponseEntity.ok().body(Map.of("Message", "Deleted pet from adoption", "PetID", id));
  }

}
