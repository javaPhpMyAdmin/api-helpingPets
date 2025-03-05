package com.marcelobatista.dev.helpingPets.src.modules.pets.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.modules.pets.application.service.PetService;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetCreateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetUpdateDTO;
import com.marcelobatista.dev.helpingPets.src.shared.Response.GlobalResponse;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
  private final PetService petService;
  private final RequestUtils requestUtils;

  @GetMapping()
  public ResponseEntity<GlobalResponse> getAllPets(HttpServletRequest request) {
    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("result", petService.getAllPets()),
        "Pets Retrieved Successfully ", HttpStatus.OK));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GlobalResponse> getSpecificPet(@PathVariable(name = "id") @Valid @NotNull Long petId,
      HttpServletRequest request) {
    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("result", petService.getPetById(petId)),
        "Pet Retrieved Successfully ", HttpStatus.OK));
  }

  @PostMapping()
  public ResponseEntity<GlobalResponse> createNewPet(@RequestBody @Valid PetCreateDTO petCreateDTO,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("result", petService.createPet(petCreateDTO)),
            "Pet Added Successfully ", HttpStatus.CREATED));
  }

  @PutMapping()
  public ResponseEntity<GlobalResponse> updatePet(@RequestBody @Valid PetUpdateDTO petUpdateDTO,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("result", petService.updatePet(petUpdateDTO)),
            "Pet Updated Successfully ", HttpStatus.OK));
  }

  @DeleteMapping("/{petId}")
  public ResponseEntity<GlobalResponse> deletePet(@PathVariable(name = "petId") @Valid @NotNull Long petId,
      HttpServletRequest request) {
    petService.deletePet(petId);
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("result", "petDto.getId() + deleted"),
            "Pet Deleted Successfully ", HttpStatus.OK));
  }

}
