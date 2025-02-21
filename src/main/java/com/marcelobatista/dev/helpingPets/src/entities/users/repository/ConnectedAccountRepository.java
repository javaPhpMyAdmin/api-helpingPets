package com.marcelobatista.dev.helpingPets.src.entities.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.entities.users.UserConnectedAccount;

@Repository
public interface ConnectedAccountRepository extends JpaRepository<UserConnectedAccount, Long> {

  @Query("SELECT u FROM UserConnectedAccount u WHERE u.provider = :provider AND u.providerId = :providerId")
  Optional<UserConnectedAccount> findByProviderAndProviderId(@Param("provider") String provider,
      @Param("providerId") String providerId);

}
