package com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.reports.application.service.FoundPetReportService;
import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.UpdateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.infrastructure.FoundPetRepository;
import com.marcelobatista.dev.helpingPets.src.modules.reports.mapper.FoundPetReportMapper;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.ImageService.UploadImage;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportStatus;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundPetReportServiceImpl implements FoundPetReportService {
  private final FoundPetRepository foundPetRepository;
  private final FoundPetReportMapper foundPetReportMapper;
  private final UploadImage uploadImage;

  @Override
  @Transactional
  public FoundPetReportDTO createReport(CreateFoundPetReportDTO createFoundPetReportDTO) {
    var currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.UNAUTHORIZED.value())
            .message("Unauthorized: user not authenticated").build());

    FoundPetReport foundPetReport = foundPetReportMapper.toEntity(createFoundPetReportDTO);
    foundPetReport.setReporter(currentUser);
    foundPetReport.setDescription(createFoundPetReportDTO.imageDTO().description());
    foundPetReport.setStatus(ReportStatus.OPEN);
    foundPetReport.setReportType(ReportType.FOUND);

    // Subir las imágenes a Cloudinary y obtener sus URLs
    if (createFoundPetReportDTO.imageDTO().image() != null
        && !createFoundPetReportDTO.imageDTO().image().isEmpty()) {

      String imageUrl = "";
      try {
        imageUrl = uploadImage.uploadImageToCloudinary(createFoundPetReportDTO.imageDTO().image(),
            ReportType.FOUND);
      } catch (IOException e) {
        throw new UncheckedIOException("Failed to upload image to Cloudinary", e);
      }

      foundPetReport.setImageUrl(imageUrl);
    }

    return foundPetReportMapper.toDto(
        Optional.of(foundPetRepository.save(foundPetReport))
            .orElseThrow(() -> ApiException.builder().status(500).message("Failed to save lostPetReport").build()));

  }

  @Override
  @Transactional(readOnly = true)
  public Page<FoundPetReportDTO> getAllReports(Pageable pageable) {
    Page<FoundPetReport> reports = foundPetRepository.findAllByOrderByCreatedAtDesc(pageable);
    return reports.map(foundPetReportMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public FoundPetReportDTO getReportById(Long foundPetId) {
    if (foundPetId == null) {
      throw ApiException.builder()
          .status(HttpStatus.BAD_REQUEST.value())
          .message("Lost Pet ID must not be null")
          .build();
    }

    return foundPetRepository.findById(foundPetId)
        .map(foundPetReportMapper::toDto)
        .orElseThrow(() -> ApiException.builder().status(HttpStatus.BAD_REQUEST.value())
            .message("LostPetReport not found").build());
  }

  @Override
  @Transactional
  public FoundPetReportDTO updateReport(UpdateFoundPetReportDTO updateFoundPetReportDTO) {
    throw new UnsupportedOperationException("Unimplemented method 'updateReport'");
  }

  @Override
  @Transactional
  public void deleteReport(Long foundPetId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteReport'");
  }

}
