package com.marcelobatista.dev.helpingPets.src.shared.exceptions;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String message) {
    super(message);
  }
}
