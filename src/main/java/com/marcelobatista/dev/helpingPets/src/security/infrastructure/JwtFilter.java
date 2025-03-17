package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.application.service.JwtService;
import com.marcelobatista.dev.helpingPets.src.security.domain.TokenData;
import com.marcelobatista.dev.helpingPets.src.shared.enums.TokenType;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String token = jwtService.extractToken(request, TokenType.ACCESS.getValue())
          .orElse(null);
      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      User user = jwtService.getTokenData(token, TokenData::getUser);
      String userEmail = user.getEmail();

      if (userEmail != null &&
          SecurityContextHolder.getContext().getAuthentication() == null) {
        if (jwtService.validateToken(token, user)) {
          UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user,
              user.getPassword());

          userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(userToken);
        }

      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      String jsonResponse = new ObjectMapper().writeValueAsString(Map.of(
          "status", HttpServletResponse.SC_UNAUTHORIZED,
          "error", "Unauthorized",
          "message", "Invalid or expired token"));
      response.getWriter().write(jsonResponse);
    }

  }

}
