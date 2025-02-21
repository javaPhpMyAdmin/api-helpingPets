package com.marcelobatista.dev.helpingPets.src.entities.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.entities.users.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
  @Query("SELECT vc FROM VerificationCode vc WHERE vc.code = :code")
  Optional<VerificationCode> findByCode(String code);

}
