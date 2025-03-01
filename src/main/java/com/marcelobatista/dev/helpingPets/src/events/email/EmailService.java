package com.marcelobatista.dev.helpingPets.src.events.email;

public interface EmailService {

  void sendNewAccountEmail(String name, String email, String token);

  void sendResetPasswordEmail(String name, String email, String token);

}
