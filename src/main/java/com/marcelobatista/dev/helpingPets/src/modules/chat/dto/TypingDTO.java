package com.marcelobatista.dev.helpingPets.src.modules.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypingDTO {
  private Long conversationId;
  private Long userId;
  private boolean isTyping;
}
