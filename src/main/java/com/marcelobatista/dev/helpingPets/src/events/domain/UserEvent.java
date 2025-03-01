package com.marcelobatista.dev.helpingPets.src.events.domain;

import java.util.Map;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.shared.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
  private User user;
  private EventType type;
  private Map<?, ?> data;

}
