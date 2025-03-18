package com.marcelobatista.dev.helpingPets.src.security.application.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.application.service.UserTokenService;
import com.marcelobatista.dev.helpingPets.src.security.domain.UserToken;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.UserTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

  private final UserTokenRepository userTokenRepository;

  // Guardar un nuevo token en la base de datos
  public void saveToken(User user, String token, Instant expiresAt) {
    userTokenRepository.revokeAllActiveTokensByUser(user.getId()); // Revocar tokens previos
    UserToken userToken = UserToken.builder()
        .user(user)
        .token(token)
        .revoked(false)
        .isLogout(false)
        .createdAt(Instant.now())
        .expiresAt(expiresAt)
        .build();
    userTokenRepository.save(userToken);
  }

  // Verificar si un token es válido
  public boolean isTokenValid(String token) {
    return userTokenRepository.findByToken(token)
        .map(t -> !t.getRevoked() && t.getExpiresAt().isAfter(Instant.now()))
        .orElse(false);
  }

  // Revocar un token específico (por logout)
  public void revokeToken(String token) {
    userTokenRepository.findByToken(token).ifPresent(t -> {
      t.setRevoked(true);
      t.setIsLogout(true);
      userTokenRepository.save(t);
    });
  }

  // Eliminar tokens expirados automáticamente (se puede llamar con una tarea
  // programada)
  @Scheduled(cron = "0 0 3 * * ?") // Todos los días a las 3 AM
  @Transactional
  public void cleanExpiredTokens() {
    userTokenRepository.deleteExpiredTokens(Instant.now());
  }

  @Override
  @Transactional
  public void revokeAllActiveTokensByUser(Long userId) {
    List<UserToken> activeTokens = userTokenRepository.findAllActiveTokensByUserId(userId);
    if (!activeTokens.isEmpty()) {
      activeTokens.forEach(token -> token.setRevoked(true));
      userTokenRepository.saveAll(activeTokens);
    }
  }
}
