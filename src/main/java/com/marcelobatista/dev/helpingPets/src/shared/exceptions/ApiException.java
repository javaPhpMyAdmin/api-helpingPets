package com.marcelobatista.dev.helpingPets.src.shared.exceptions;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  @Builder.Default
  private int status = 400;
  private String message;
  private Map<String, String> errors;

}
// @Getter
// public class ApiException extends ResponseStatusException {

// private final Map<String, String> errors;

// public ApiException(int status, String message, Map<String, String> errors) {
// super(HttpStatus.valueOf(status), message);
// this.errors = errors;
// }
// }
