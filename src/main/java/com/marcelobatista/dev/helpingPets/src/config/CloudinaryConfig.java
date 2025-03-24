package com.marcelobatista.dev.helpingPets.src.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import jakarta.annotation.PostConstruct;

@Configuration
public class CloudinaryConfig {

  @Value("${cloudinary.cloud-namee}")
  private String cloudName;
  @Value("${cloudinary.api-keyy}")
  private String apiKey;
  @Value("${cloudinary.api-secret}")
  private String apiSecret;

  @PostConstruct
  public void printConfig() {
    System.out.println("Cloudinary Config:");
    System.out.println("cloud_name: " + cloudName);
    System.out.println("api_key: " + apiKey);
    System.out.println("api_secret: " + (apiSecret != null ? "******" : "NULL"));
  }

  // @Bean
  // Cloudinary cloudinary() {
  // return new Cloudinary(Map.of(
  // "cloud_name", cloudName,
  // "api_key", apiKey,
  // "api_secret", apiSecret,
  // "secure", true));
  // }

  @Bean
  Cloudinary cloudinary() {
    return new Cloudinary(Map.of(
        "cloud_name",
        "dh27sb79z",
        "api_key", "394126938776799",
        "api_secret", "NeA43-iYXyHtWYAi1iDh46wRHPw",
        "secure", true));
  }
}
