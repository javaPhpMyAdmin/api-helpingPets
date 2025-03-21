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
  private static final String ERROR_SERIALIZING_LOGOUT_RESPONSE = "Error serializing logout response";
  private static final String THE_USER_WAS_LOGGED_OUT_CORRECTLY = "The user was logged out correctly";
  private static final String LOGOUT_SUCCESS_TOKEN_REVOKED = "Logout Success, token revoked";
  private static final String RESULT = "result";
  private static final String UTF_8 = "UTF-8";
  private static final String APPLICATION_JSON = "application/json";
  private final RequestUtils requestUtils;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, java.io.IOException {
    response.setContentType(APPLICATION_JSON);
    response.setCharacterEncoding(UTF_8);
    response.setStatus(HttpServletResponse.SC_OK);
    String jsonResponse;
    try {
      jsonResponse = objectMapper.writeValueAsString(
          requestUtils.getResponse(
              request,
              Map.of(RESULT, LOGOUT_SUCCESS_TOKEN_REVOKED),
              THE_USER_WAS_LOGGED_OUT_CORRECTLY,
              HttpStatus.OK));
    } catch (JsonProcessingException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_SERIALIZING_LOGOUT_RESPONSE);
      return;
    }

    response.getWriter().write(jsonResponse);
    response.getWriter().flush();
  }
}
