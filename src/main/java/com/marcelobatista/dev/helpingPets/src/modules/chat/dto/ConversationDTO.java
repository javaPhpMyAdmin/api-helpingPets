package com.marcelobatista.dev.helpingPets.src.modules.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
  Long conversationId;
  Long senderId;
  Long receiverId;
  // List<ChatMessageDTO> messages;
}
