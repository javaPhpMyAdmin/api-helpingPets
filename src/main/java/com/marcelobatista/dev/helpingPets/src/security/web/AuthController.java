package com.marcelobatista.dev.helpingPets.src.security.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.security.application.service.AuthService;
import com.marcelobatista.dev.helpingPets.src.security.dto.LoginRequestDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
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

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }

    // Eliminar cookie manualmente
    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    return ResponseEntity.ok("Logout exitoso");
  }

  @GetMapping("/login-success")
  public String auth() {
    return "LOGIN SUCCESS";
  }

}
