package com.marcelobatista.dev.helpingPets.src.modules.errorTest.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.modules.pets.infrastructure.PetRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class errorControllerTest {

  private final PetRepository petRepository;

  @GetMapping("/internal-error")
  public ResponseEntity<String> triggerError() {
    throw new RuntimeException("Simulación de error interno");
  }

  @SuppressWarnings("null")
  @GetMapping("/null-pointer")
  public void triggerNullPointerException() {
    String str = null;
    str.length();
  }

  @GetMapping("/sql-error")
  public List<PetEntity> triggerDatabaseError() {
    return petRepository.findByName("Firulais");
  }
}
