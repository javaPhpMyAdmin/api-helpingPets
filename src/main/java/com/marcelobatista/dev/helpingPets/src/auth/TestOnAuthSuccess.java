package com.marcelobatista.dev.helpingPets.src.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestOnAuthSuccess implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    log.info("HERE WE ARE IN THE OAUTH2LOGIN SUCCESS HANDLER");
    System.out.println("HERE WE ARE IN THE OAUTH2LOGIN SUCCESS HANDLER");
  }

}
