package com.marcelobatista.dev.helpingPets.src.modules.reports.web;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.PetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CoordinatesDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateImageDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.LocationDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.UpdateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.shared.Response.GlobalResponse;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;
import com.marcelobatista.dev.helpingPets.src.shared.utils.Pageable.PageableHelper;

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
  private final PetReportService petReportService;

  @GetMapping()
  public ResponseEntity<?> getAllReports(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, HttpServletRequest request) {

    Pageable pageable = PageableHelper.createPageable(page, size);

    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", petReportService.getAllReports(pageable)),
            "PetReports retrieved", HttpStatus.OK));
  }

  @GetMapping("/lost-pet")
  public ResponseEntity<?> getAllLostPetReports(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, HttpServletRequest request) {

    Pageable pageable = PageableHelper.createPageable(page, size);

    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", lostPetService.getAllReports(pageable)),
            "LostPetReports retrieved", HttpStatus.OK));
  }

  @GetMapping("/found-pet")
  public ResponseEntity<?> getAllFoundPetReports(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, HttpServletRequest request) {

    Pageable pageable = PageableHelper.createPageable(page, size);

    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", foundPetService.getAllReports(pageable)),
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

  @PostMapping(value = "/lost-pet", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<GlobalResponse> createLostPetReport(
      @RequestPart("petName") String petName,
      @RequestPart("title") String title,
      @RequestPart("breed") String breed,
      @RequestPart("description") String description,
      @RequestPart("contactEmail") String contactEmail,
      @RequestPart("imageUrls") List<MultipartFile> imageUrls,
      HttpServletRequest request) {
    CreateLostPetReportDTO createLostPetReportDTO = CreateLostPetReportDTO.builder()
        .petName(petName)
        .breed(breed)
        .description(description)
        .contactEmail(contactEmail)
        .imageUrls(imageUrls)
        .title(title)
        .build();
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result", lostPetService.createReport(createLostPetReportDTO)),
            "New Report Added", HttpStatus.CREATED));
  }

  @PostMapping(value = "/found-pet", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createFoundPetReport(
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("latitude") Double latitude,
      @RequestParam("longitude") Double longitude,
      @RequestParam("foundPetStatus") String foundPetStatus,
      @RequestParam("imageUrl") MultipartFile imageUrl,
      HttpServletRequest request) {

    CreateImageDTO imageDto = new CreateImageDTO(imageUrl, description);
    CoordinatesDTO coordinatesDTO = new CoordinatesDTO(latitude, longitude);
    LocationDTO location = new LocationDTO(coordinatesDTO);

    CreateFoundPetReportDTO createFoundPetReportDTO = new CreateFoundPetReportDTO(
        title,
        imageDto,
        location,
        foundPetStatus);

    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("Result",
            foundPetService.createReport(createFoundPetReportDTO)),
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
