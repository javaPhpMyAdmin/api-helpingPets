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

  @GetMapping()
  public ResponseEntity<?> getAllReports() {
    return ResponseEntity.ok().body(Map.of("Message", "Getting all Reports"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getSpecificReport(@PathVariable(name = "id") @Valid @NotNull int id) {
    return ResponseEntity.ok().body(Map.of("Message", "Getting Report of pet"));
  }

  @PostMapping()
  public ResponseEntity<?> addNewReport(@RequestBody @Valid String ReportDTO) {
    return ResponseEntity.ok().body(Map.of("Message", "New Report Added"));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateReport(@PathVariable("id") int id, @RequestBody @Valid String UpdateReportDTO) {
    return ResponseEntity.ok().body(Map.of("ReportId", id, "Body Report", UpdateReportDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePet(@PathVariable("id") @Valid @NotNull int id) {
    return ResponseEntity.ok().body(Map.of("Message", "Deleted Report", "ReportID", id));

  }

}
