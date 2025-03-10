package com.marcelobatista.dev.helpingPets.src.shared.ImageService;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UploadImage {
  private final ImageService imageService;

  public String uploadImageToCloudinary(MultipartFile image, ReportType reportType) throws IOException {
    return imageService.uploadImage(image, reportType);
  }
}
