package com.marcelobatista.dev.helpingPets.src.shared.ImageService;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {

  private final Cloudinary cloudinary;

  public ImageService(
      @Value("${cloudinary.cloud-name}") String cloudName,
      @Value("${cloudinary.api-key}") String apiKey,
      @Value("${cloudinary.api-secret}") String apiSecret) {
    this.cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", cloudName,
        "api_key", apiKey,
        "api_secret", apiSecret));
  }

  @SuppressWarnings("unchecked")
  public String uploadImage(MultipartFile file) throws IOException {
    Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
        ObjectUtils.asMap("folder", "helping_pets_images"));

    return (String) uploadResult.get("secure_url"); // URL segura de la imagen subida
  }
}
