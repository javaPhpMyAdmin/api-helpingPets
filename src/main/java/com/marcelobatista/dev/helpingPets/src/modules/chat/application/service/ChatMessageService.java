package com.marcelobatista.dev.helpingPets.src.modules.chat.application.service;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ChatMessageDTO;

public interface ChatMessageService {
  ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO);

  List<ChatMessageDTO> getMessages(Long conversationId);
}
