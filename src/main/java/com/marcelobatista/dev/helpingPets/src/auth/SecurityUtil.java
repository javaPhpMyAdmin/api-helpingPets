package com.marcelobatista.dev.helpingPets.src.auth;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

import com.marcelobatista.dev.helpingPets.src.entities.users.User;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {

  public static User getAuthenticatedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof User user) {
      return user;
    } else {
      log.error("User requested but not found in SecurityContextHolder");
      throw ApiException.builder().status(401).message("Authentication required").build();
    }
  }

  public static Optional<User> getOptionalAuthenticatedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User user) {
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }
}
