package com.marcelobatista.dev.helpingPets.src.events.user;

import java.util.Map;

import com.marcelobatista.dev.helpingPets.src.entities.users.User;
import com.marcelobatista.dev.helpingPets.src.enumeration.EventType;

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
