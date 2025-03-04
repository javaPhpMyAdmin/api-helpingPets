package com.marcelobatista.dev.helpingPets.src.security.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.security.application.service.AuthService;
import com.marcelobatista.dev.helpingPets.src.security.dto.LoginRequestDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication managment")
@Slf4j
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
      @Valid @RequestBody LoginRequestDTO loginRequest) {
    authService.login(request, response, loginRequest);
    Map<String, String> message = Map.of("message", "LOGIN SUCCESS TEST IMPROVED ....");
    return ResponseEntity.ok().body(message);
  }

  @GetMapping("/login-success")
  public String auth() {
    return "LOGIN SUCCESS";
  }

}
