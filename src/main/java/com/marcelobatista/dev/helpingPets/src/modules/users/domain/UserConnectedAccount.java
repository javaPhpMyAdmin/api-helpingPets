package com.marcelobatista.dev.helpingPets.src.modules.users.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_connected_accounts")
public class UserConnectedAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String provider;
  private String providerId;
  private LocalDateTime connectedAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public UserConnectedAccount(String provider, String providerId, User user) {
    this.provider = provider;
    this.providerId = providerId;
    this.connectedAt = LocalDateTime.now();
    this.user = user;
  }
}
