package com.marcelobatista.dev.helpingPets.src.shared.exceptions;

public class TokenRevokedException extends RuntimeException {
  public TokenRevokedException(String message) {
    super(message);
  }
}
