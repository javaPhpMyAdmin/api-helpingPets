package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.LostPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.UpdateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure.LostPetReportRepository;
import com.marcelobatista.dev.helpingPets.src.modules.reports.mapper.LostPetReportMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.ImageService.UploadImage;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LostPetReportServiceImpl implements LostPetReportService {
  private final LostPetReportRepository lostPetReportRepository;
  private final LostPetReportMapper lostPetReportMapper;
  private final UploadImage uploadImage;

  @Override
  @Transactional
  public LostPetReportDTO createReport(CreateLostPetReportDTO createLostPetReportDTO) {

    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized: user not authenticated").build());

    var lostPetReport = lostPetReportMapper.toEntity(createLostPetReportDTO);
    lostPetReport.setReporter(currentUser);
    lostPetReport.setStatus(ReportStatus.OPEN);
    lostPetReport.setReportType(ReportType.LOST);

    // Subir las imágenes a Cloudinary y obtener sus URLs
    if (createLostPetReportDTO.getImageUrls() != null && !createLostPetReportDTO.getImageUrls().isEmpty()) {
      List<String> imageUrls = createLostPetReportDTO.getImageUrls().stream()
          .map(t -> {
            try {
              return uploadImage.uploadImageToCloudinary(t, ReportType.LOST);
            } catch (IOException e) {
              throw new UncheckedIOException("Failed to upload image to Cloudinary", e);
            }
          })
          .collect(Collectors.toList());
      lostPetReport.setImageUrls(imageUrls);
    }

    return lostPetReportMapper.toDto(
        Optional.of(lostPetReportRepository.save(lostPetReport))
            .orElseThrow(() -> ApiException.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Failed to save lostPetReport").build()));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LostPetReportDTO> getAllReports(Pageable pageable) {
    Page<LostPetReport> reports = lostPetReportRepository.findAllByOrderByReportedAtDesc(pageable);
    return reports.map(lostPetReportMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public LostPetReportDTO getReportById(Long lostPetId) {
    if (lostPetId == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Lost Pet ID must not be null")
          .build();
    }

    return lostPetReportRepository.findById(lostPetId)
        .map(lostPetReportMapper::toDto)
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.BAD_REQUEST.value())
            .message("LostPetReport not found").build());
  }

  @Override
  @Transactional
  public LostPetReportDTO updateReport(UpdateLostPetReportDTO updateLostPetReportDTO) {

    if (updateLostPetReportDTO.getLostPetReportId() == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Lost Pet Report Id must not be null")
          .build();
    }

    LostPetReport lostPetReport = lostPetReportRepository.findById(updateLostPetReportDTO.getLostPetReportId())
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.BAD_REQUEST.value())
            .message(("LostPetReport with this id " + updateLostPetReportDTO.getLostPetReportId() + " not found"))
            .build());

    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized: user not authenticated").build());

    if (!currentUser.getId().equals(lostPetReport.getReporter().getId())) {
      throw ApiException.builder().status(HttpStatus.FORBIDDEN.value())
          .message("Forbidden: user not allowed to update this report").build();
    }

    lostPetReport.updateFromDto(updateLostPetReportDTO);

    return lostPetReportMapper.toDto(lostPetReport);
  }

  @Override
  @Transactional
  public void deleteReport(Long lostPetId) {
    if (lostPetId == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Lost Pet ID must not be null")
          .build();
    }

    // Obtener el usuario autenticado
    User currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized: User not authenticated")
            .build());

    // Buscar la mascota
    LostPetReport existingPet = lostPetReportRepository.findById(lostPetId)
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message("Lost Pet Report not found")
            .build());

    // Verificar si el usuario autenticado es el dueño de la mascota
    if (!existingPet.getReporter().getId().equals(currentUser.getId())) {
      throw ApiException.builder()
          .status(HttpStatus.FORBIDDEN.value())
          .message("You are not the owner of this pet")
          .build();
    }

    lostPetReportRepository.delete(existingPet);
    // Log de auditoría
    log.info("Lost Pet Report with ID {} deleted by user {}", lostPetId, currentUser.getId());
  }

}
