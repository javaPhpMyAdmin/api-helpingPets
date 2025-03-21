package com.marcelobatista.dev.helpingPets.src.security.application.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UserResponse;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.security.domain.Token;
import com.marcelobatista.dev.helpingPets.src.security.domain.TokenData;
import com.marcelobatista.dev.helpingPets.src.security.dto.LoginRequestDTO;

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
  private final AuthenticationManager authenticationManager;
  private final UserTokenService userTokenService;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

  @Transactional
  public UserResponse login(HttpServletRequest request, HttpServletResponse response, LoginRequestDTO body)
      throws AuthenticationException {

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(body.getEmail(),
        body.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);

    User user = userRepository.findByEmail(body.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    userTokenService.revokeAllActiveTokensByUser(user.getId());

    String jwt = jwtService.createToken(user, Token::getAccess);
    String refreshJwt = jwtService.createToken(user, Token::getRefresh);

    Instant expiresAt = jwtService.getTokenData(jwt, TokenData::getExpiration);

    userTokenService.saveToken(user, jwt, expiresAt);

    // Save the authentication in the SecurityContext
    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
    context.setAuthentication(authentication);
    securityContextHolderStrategy.setContext(context);
    securityContextRepository.saveContext(context, request, response);

    return new UserResponse(user, jwt, refreshJwt);
  }

}
