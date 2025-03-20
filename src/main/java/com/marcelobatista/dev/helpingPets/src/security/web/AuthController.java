package com.marcelobatista.dev.helpingPets.src.security.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.security.application.service.AuthService;
import com.marcelobatista.dev.helpingPets.src.security.dto.LoginRequestDTO;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;
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

  private static final String LOGIN_SUCCESS = "/login-success";
  private static final String PING = "/ping";
  private static final String LOGOUT = "/logout";
  private static final String LOGIN = "/login";

  private final RequestUtils requestUtils;

  private final AuthService authService;

  @PostMapping(LOGIN)
  public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
      @Valid @RequestBody LoginRequestDTO loginRequest) {
    var userLogged = authService.login(request, response, loginRequest);
    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("result", userLogged),
        "User logged successfully.", HttpStatus.OK));
  }

  @PostMapping(LOGOUT)
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    authService.logout(request, response);
    return ResponseEntity.ok().body(
        requestUtils.getResponse(
            request,
            Map.of("Token", "Logout Success, token revoked"),
            "The user was logged out correctly",
            HttpStatus.OK));
  }

  @GetMapping(LOGIN_SUCCESS)
  public String auth() {
    return "LOGIN SUCCESS";
  }

  @GetMapping(PING)
  public ResponseEntity<?> testConection() {
    return ResponseEntity.ok().body(Map.of("Message", "Server Running"));
  }

}
