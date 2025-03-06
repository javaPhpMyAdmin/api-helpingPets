package com.marcelobatista.dev.helpingPets.src.modules.reports.web;

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

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Reports managment")
@Slf4j
public class ReportController {

  private final LostPetReportService lostPetService;
  private final FoundPetReportService foundPetService;

  @GetMapping()
  public ResponseEntity<?> getAllReports() {
    return ResponseEntity.ok().body(Map.of("Message", "Getting all Reports"));
  }

  @GetMapping("/lost-pet")
  public ResponseEntity<?> getAllLostPetReports() {
    return ResponseEntity.ok().body(Map.of("Message", "Getting Report of pet"));
  }

  @GetMapping("/found-pet")
  public ResponseEntity<?> getAllFoundPetReports() {
    return ResponseEntity.ok().body(Map.of("Message", "Getting Report of pet"));
  }

  @GetMapping("/lost-pet/{petId}")
  public ResponseEntity<?> getLostPetReportById(@PathVariable Long petId) {
    return ResponseEntity.ok().body(Map.of("Message", "Getting Report of pet"));
  }

  @GetMapping("/found-pet/{petId}")
  public ResponseEntity<?> getFoundPetReportById(@PathVariable Long petId) {
    return ResponseEntity.ok().body(Map.of("Message", "Getting Report of pet"));
  }

  @PostMapping("/lost-pet")
  public ResponseEntity<?> createLostPetReport(@RequestBody @Valid LostPetReportDTO lostPetReportDTO) {
    return ResponseEntity.ok().body(Map.of("Message", "New Report Added"));
  }

  @PostMapping("/found-pet")
  public ResponseEntity<?> createFoundPetReport(@RequestBody @Valid FoundPetReportDTO foundPetReportDTO) {
    return ResponseEntity.ok().body(Map.of("Message", "New Report Added"));
  }

  @PutMapping("/lost-pet/{petId}")
  public ResponseEntity<?> updateLostPetReport(@PathVariable() Long petId, @RequestBody @Valid String UpdateReportDTO) {
    return ResponseEntity.ok().body(Map.of("ReportId", petId, "Body Report", UpdateReportDTO));
  }

  @PutMapping("/found-pet/{petId}")
  public ResponseEntity<?> updateFoundPetReport(@PathVariable() Long petId,
      @RequestBody @Valid String UpdateReportDTO) {
    return ResponseEntity.ok().body(Map.of("ReportId", petId, "Body Report", UpdateReportDTO));
  }

  @DeleteMapping("/lost-pet/{petId}")
  public ResponseEntity<?> deleteLostPetReport(@PathVariable() @Valid @NotNull Long petId) {
    return ResponseEntity.ok().body(Map.of("Message", "Deleted Report", "ReportID", petId));

  }

  @DeleteMapping("/found-pet/{petId}")
  public ResponseEntity<?> deleteFoundPetReport(@PathVariable() @Valid @NotNull Long petId) {
    return ResponseEntity.ok().body(Map.of("Message", "Deleted Report", "ReportID", petId));

  }

}
