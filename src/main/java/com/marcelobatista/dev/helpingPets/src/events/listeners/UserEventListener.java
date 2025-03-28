package com.marcelobatista.dev.helpingPets.src.events.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.marcelobatista.dev.helpingPets.src.events.domain.UserEvent;
import com.marcelobatista.dev.helpingPets.src.events.email.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventListener {
  private final EmailService emailService;

  @EventListener
  public void onUserEvent(UserEvent event) {
    switch (event.getType()) {
      case REGISTRATION -> emailService.sendNewAccountEmail(event.getUser().getFirstName(), event.getUser().getEmail(),
          (String) event.getData().get("token"));
      case RESETPASSWORD ->
        emailService.sendResetPasswordEmail(event.getUser().getFirstName(), event.getUser().getEmail(),
            (String) event.getData().get("token"));
      default -> {
      }
    }

  }

}
