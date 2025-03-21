package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.marcelobatista.dev.helpingPets.src.security.application.service.JwtService;
import com.marcelobatista.dev.helpingPets.src.security.application.service.UserTokenService;
import com.marcelobatista.dev.helpingPets.src.shared.enums.TokenType;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
  @Lazy
  private final JwtService jwtService;
  private final UserTokenService userTokenService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      SecurityContextHolder.clearContext();
      logoutHandler.logout(request, response, authentication);
    }
    revokeTokenIfExists(request);
    clearCookie(response);
  }

  private void revokeTokenIfExists(HttpServletRequest request) {
    String existingToken = jwtService.extractToken(request, TokenType.ACCESS.getValue()).orElse(null);
    if (existingToken != null) {
      userTokenService.revokeToken(existingToken);
    }
  }

  private void clearCookie(HttpServletResponse response) {
    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }

}
