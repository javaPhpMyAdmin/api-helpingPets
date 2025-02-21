package com.marcelobatista.dev.helpingPets.src.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.auth.data.LoginRequest;
import com.marcelobatista.dev.helpingPets.src.entities.users.repository.UserRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

  public void login(HttpServletRequest request, HttpServletResponse response, LoginRequest body)
      throws AuthenticationException {

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(body.getEmail(),
        body.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);

    // Save the authentication in the SecurityContext
    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
    context.setAuthentication(authentication);
    securityContextHolderStrategy.setContext(context);
    securityContextRepository.saveContext(context, request, response);

  }
}
