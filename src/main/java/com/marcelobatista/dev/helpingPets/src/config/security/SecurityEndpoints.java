package com.marcelobatista.dev.helpingPets.src.config.security;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class SecurityEndpoints {

  public static final List<String> PUBLIC_ENDPOINTS = List.of(
      "/auth/**",
      "/user/create-user",
      "/user/verify/**",
      "/pets/public/**",
      "/reports/public/**");

  public static final Map<HttpMethod, List<String>> USER_PROTECTED_ENDPOINTS = Map.of(HttpMethod.PUT, List.of(
      "/user/update-user"),
      HttpMethod.GET, List.of("/user/me/**",
          "/adoptions/**"),
      HttpMethod.POST, List.of(
          "/reports"));

  public static final Map<HttpMethod, List<String>> ADMIN_PROTECTED_ENDPOINTS = Map.of(HttpMethod.GET, List.of(
      "/admin/**"),
      HttpMethod.DELETE, List.of("/user/{id}"));

  // To prevent init
  private SecurityEndpoints() {
  };
}
