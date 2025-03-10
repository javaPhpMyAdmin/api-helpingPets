package com.marcelobatista.dev.helpingPets.src.shared.ImageService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.marcelobatista.dev.helpingPets.src.shared.enums.ReportType;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

@Service
public class ImageService {

  private static final String ADOPTION_PETS = "helping_pets_images/AdoptionPets";
  private static final String FOUND_PET_REPORTS = "helping_pets_images/FoundPetReports";
  private static final String LOST_PET_REPORTS = "helping_pets_images/LostPetReports";
  private final Cloudinary cloudinary;

  public ImageService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @SuppressWarnings("unchecked")
  public String uploadImage(MultipartFile file, ReportType reportType) throws IOException {

    List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "webp", "avif");

    String extensions = null;

    if (file.getOriginalFilename() != null) {
      @SuppressWarnings("null")
      String[] splitName = file.getOriginalFilename().split("\\.");
      extensions = splitName[splitName.length - 1];
    }

    if (!allowedExtensions.contains(extensions))
      throw ApiException.builder().message(String.format("Extension %s not allowed", extensions)).build();

    String folderName = switch (reportType) {
      case LOST -> LOST_PET_REPORTS;
      case FOUND -> FOUND_PET_REPORTS;
      default -> ADOPTION_PETS;
    };

    Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
        ObjectUtils.asMap("folder", folderName));

    return (String) uploadResult.get("secure_url");
  }
}
