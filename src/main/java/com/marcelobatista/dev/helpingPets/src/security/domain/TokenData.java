package com.marcelobatista.dev.helpingPets.src.security.domain;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenData {
  private User user;
  private boolean valid;
  private Claims claims;
  List<GrantedAuthority> authorities;
  private Instant expiration;

}
