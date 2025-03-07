package com.marcelobatista.dev.helpingPets.src.modules.reports.web;

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

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.UpdateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
  private final RequestUtils requestUtils;

  @GetMapping()
  public ResponseEntity<?> getAllReports() {
    return ResponseEntity.ok().body(Map.of("Message", "Getting all Reports"));
  }

  @GetMapping("/lost-pet")
  public ResponseEntity<?> getAllLostPetReports(HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", lostPetService.getAllReports()),
            "LostPetReports retrieved", HttpStatus.OK));
  }

  @GetMapping("/found-pet")
  public ResponseEntity<?> getAllFoundPetReports(HttpServletRequest request) {
    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("Result", foundPetService.getAllReports()),
        "FoundPetReports retrieved", HttpStatus.OK));
  }

  @GetMapping("/lost-pet/{petId}")
  public ResponseEntity<?> getLostPetReportById(@PathVariable Long petId, HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", lostPetService.getReportById(petId)),
            "LostPetReport retrieved", HttpStatus.OK));
  }

  @GetMapping("/found-pet/{petId}")
  public ResponseEntity<?> getFoundPetReportById(@PathVariable Long petId, HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", foundPetService.getReportById(petId)),
            "FoundPetReport retrieved", HttpStatus.OK));
  }

  @PostMapping("/lost-pet")
  public ResponseEntity<?> createLostPetReport(@RequestBody @Valid CreateLostPetReportDTO createLostPetReportDTO,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", lostPetService.createReport(createLostPetReportDTO)),
            "New Report Added", HttpStatus.CREATED));
  }

  @PostMapping("/found-pet")
  public ResponseEntity<?> createFoundPetReport(@RequestBody @Valid FoundPetReportDTO foundPetReportDTO,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", foundPetService.createReport(foundPetReportDTO)),
            "New Report Added", HttpStatus.CREATED));
  }

  @PutMapping("/lost-pet")
  public ResponseEntity<?> updateLostPetReport(
      @RequestBody @Valid UpdateLostPetReportDTO updateLostPetReportDTO,
      HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", lostPetService.updateReport(updateLostPetReportDTO)),
            "Report Updated", HttpStatus.OK));
  }

  @PutMapping("/found-pet/{petId}")
  public ResponseEntity<?> updateFoundPetReport(@PathVariable() Long petId,
      @RequestBody @Valid UpdateFoundPetReportDTO updateReportDTO, HttpServletRequest request) {
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", foundPetService.updateReport(updateReportDTO)),
            "Report Updated", HttpStatus.OK));
  }

  @DeleteMapping("/lost-pet/{petId}")
  public ResponseEntity<?> deleteLostPetReport(@PathVariable() @Valid @NotNull Long petId, HttpServletRequest request) {
    lostPetService.deleteReport(petId);
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Message", "Deleted Report", "ReportID", petId),
            "Report Deleted", HttpStatus.OK));

  }

  @DeleteMapping("/found-pet/{petId}")
  public ResponseEntity<?> deleteFoundPetReport(@PathVariable() @Valid @NotNull Long petId,
      HttpServletRequest request) {
    return ResponseEntity.ok().body(Map.of("Message", "Deleted Report", "ReportID", petId));

  }

}
