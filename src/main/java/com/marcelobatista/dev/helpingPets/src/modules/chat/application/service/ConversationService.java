package com.marcelobatista.dev.helpingPets.src.modules.chat.application.service;

import java.util.List;
import java.util.Optional;

import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ConversationDTO;

public interface ConversationService {
  ConversationDTO startConversation(ConversationDTO conversationDTO);

  Optional<ConversationDTO> findConversation(ConversationDTO conversationDTO);

  List<ConversationDTO> getUserConversations(Long userId);
}
