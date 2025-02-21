package com.marcelobatista.dev.helpingPets.src.entities.users;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import com.marcelobatista.dev.helpingPets.src.shared.utils.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Client
public class VerificationCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String code;
  @Setter
  private boolean emailSent = false;
  @OneToOne
  private User user;

  public VerificationCode(User user) {
    this.user = user;
    this.code = UUID.randomUUID().toString();
  }
}
