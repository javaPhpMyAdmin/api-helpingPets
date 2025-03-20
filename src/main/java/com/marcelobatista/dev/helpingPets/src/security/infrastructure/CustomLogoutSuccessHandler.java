package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
  private final RequestUtils requestUtils;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try {
      String jsonResponse = new ObjectMapper().writeValueAsString(
          requestUtils.getResponse(
              request,
              Map.of("Token", "Logout Success, token revoked"),
              "The user was logged out correctly",
              HttpStatus.OK));

      response.getWriter().write(jsonResponse);
      response.getWriter().flush();
    } catch (JsonProcessingException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing logout response");
    }
  }
}
