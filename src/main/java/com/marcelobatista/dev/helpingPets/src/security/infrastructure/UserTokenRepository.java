package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marcelobatista.dev.helpingPets.src.security.domain.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
  Optional<UserToken> findByToken(String token);

  boolean existsByTokenAndRevokedTrue(String token);

  @Query("SELECT t FROM UserToken t WHERE t.user.id = :userId AND t.revoked = false")
  List<UserToken> findAllActiveTokensByUserId(@Param("userId") Long userId);

  @Modifying
  @Transactional
  @Query("UPDATE UserToken t SET t.revoked = true WHERE t.user.id = :userId AND t.revoked = false")
  void revokeAllActiveTokensByUser(@Param("userId") Long userId);

  // Eliminar tokens expirados automáticamente
  @Modifying
  @Transactional
  @Query("DELETE FROM UserToken t WHERE t.expiresAt < :now")
  void deleteExpiredTokens(@Param("now") Instant now);
}
