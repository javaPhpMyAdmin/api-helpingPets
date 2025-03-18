package com.marcelobatista.dev.helpingPets.src.security.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.security.application.service.AuthService;
import com.marcelobatista.dev.helpingPets.src.security.application.service.JwtService;
import com.marcelobatista.dev.helpingPets.src.security.application.service.UserTokenService;
import com.marcelobatista.dev.helpingPets.src.security.dto.LoginRequestDTO;
import com.marcelobatista.dev.helpingPets.src.shared.enums.TokenType;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;
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

  private final RequestUtils requestUtils;

  private final AuthService authService;
  private final JwtService jwtService;
  private final UserTokenService userTokenService;

  @PostMapping("/login")
  public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
      @Valid @RequestBody LoginRequestDTO loginRequest) {
    var userLogged = authService.login(request, response, loginRequest);
    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("result", userLogged),
        "User logged successfully.", HttpStatus.OK));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
      SecurityContextHolder.clearContext();
    }

    // Eliminar cookie manualmente
    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    String existingToken = jwtService.extractToken(request, TokenType.ACCESS.getValue()).orElse(null);
    if (existingToken != null) {
      userTokenService.revokeToken(existingToken);
    }

    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("Token", "Logout Success, token revoked"),
        "The user was logged out correctly", HttpStatus.OK));

  }

  @GetMapping("/login-success")
  public String auth() {
    return "LOGIN SUCCESS";
  }

  @GetMapping("/ping")
  public ResponseEntity<?> testConection() {
    return ResponseEntity.ok().body(Map.of("Message", "Server Running"));
  }

}
