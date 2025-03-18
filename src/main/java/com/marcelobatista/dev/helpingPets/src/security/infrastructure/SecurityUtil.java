package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {

  public static User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null ||
        !(authentication.getPrincipal() instanceof User user)) {
      log.error("User requested but not found in SecurityContextHolder");
      return null;
    }
    return user;
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
