package com.marcelobatista.dev.helpingPets.src.events.user.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.marcelobatista.dev.helpingPets.src.events.user.UserEvent;
import com.marcelobatista.dev.helpingPets.src.service.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventListener {
  private final EmailService emailService;

  @EventListener
  public void onUserEvent(UserEvent event) {
    switch (event.getType()) {
      case REGISTRATION -> emailService.sendNewAccountEmail(event.getUser().getFirstName(), event.getUser().getEmail(),
          (String) event.getData().get("key"));
      case RESETPASSWORD ->
        emailService.sendResetPasswordEmail(event.getUser().getFirstName(), event.getUser().getEmail(),
            (String) event.getData().get("key"));
      default -> {
      }
    }

  }

}
