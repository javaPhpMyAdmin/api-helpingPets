package com.marcelobatista.dev.helpingPets.src.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.auth.data.LoginRequest;
import com.marcelobatista.dev.helpingPets.src.auth.service.AuthService;

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
@Slf4j
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
      @Valid @RequestBody LoginRequest loginRequest) {
    authService.login(request, response, loginRequest);
    Map<String, String> message = Map.of("message", "LOGIN SUCCESS");
    return ResponseEntity.ok().body(message);
  }

  @GetMapping("/login-success")
  public String auth() {
    return "LOGIN SUCCESS";
  }

}
