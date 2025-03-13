package com.marcelobatista.dev.helpingPets.src.config.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtConfig {

  @Value("${jwt.expiration_token}")
  private Long expirationToken;
  @Value("${jwt.secret_key}")
  private String secretKey;

}
