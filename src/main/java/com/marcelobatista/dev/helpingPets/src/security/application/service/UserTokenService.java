package com.marcelobatista.dev.helpingPets.src.security.application.service;

import java.time.Instant;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

public interface UserTokenService {
  public void revokeAllActiveTokensByUser(Long userId);

  public void saveToken(User user, String token, Instant expiresAt);

  public boolean isTokenValid(String token);

  public void revokeToken(String token);

  public void cleanExpiredTokens();

  public boolean isTokenRevoked(String token);
}
