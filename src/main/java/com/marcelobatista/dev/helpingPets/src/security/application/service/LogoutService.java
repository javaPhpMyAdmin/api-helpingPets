package com.marcelobatista.dev.helpingPets.src.security.application.service;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.shared.enums.TokenType;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService {
  private final JwtService jwtService;
  private final UserTokenService userTokenService;

  public LogoutService(JwtService jwtService, UserTokenService userTokenService) {
    this.jwtService = jwtService;
    this.userTokenService = userTokenService;
  }

  public void handleLogout(HttpServletRequest request, HttpServletResponse response) {
    String existingToken = jwtService.extractToken(request, TokenType.ACCESS.getValue()).orElse(null);
    if (existingToken != null) {
      userTokenService.revokeToken(existingToken);
    }

    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
