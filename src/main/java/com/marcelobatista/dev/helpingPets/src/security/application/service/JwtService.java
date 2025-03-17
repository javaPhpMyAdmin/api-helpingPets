package com.marcelobatista.dev.helpingPets.src.security.application.service;

import java.util.Optional;
import java.util.function.Function;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.domain.Token;
import com.marcelobatista.dev.helpingPets.src.security.domain.TokenData;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
  String createToken(User user, Function<Token, String> tokenFunction);

  Optional<String> extractToken(HttpServletRequest request, String tokenType);

  <T> T getTokenData(String token, Function<TokenData, T> tokenFunction);

  boolean validateToken(String token, User user);
}
