package com.marcelobatista.dev.helpingPets.src.modules.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
  Long senderId;
  Long conversationId;
  String content;
}
