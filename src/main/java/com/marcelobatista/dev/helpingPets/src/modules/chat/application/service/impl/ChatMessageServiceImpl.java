package com.marcelobatista.dev.helpingPets.src.modules.chat.application.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.chat.application.service.ChatMessageService;
import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ChatMessageEntity;
import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ConversationEntity;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ChatMessageDTO;
import com.marcelobatista.dev.helpingPets.src.modules.chat.infrastructure.ChatMessageRepository;
import com.marcelobatista.dev.helpingPets.src.modules.chat.infrastructure.ConversationRepository;
import com.marcelobatista.dev.helpingPets.src.modules.chat.mapper.ChatMessageMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

  private final ChatMessageRepository messageRepository;
  private final ConversationRepository conversationRepository;
  // private final SimpMessagingTemplate messagingTemplate;
  private final UserRepository userRepository;
  private final ChatMessageMapper messageMapper;

  @Override
  public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
    ConversationEntity conversation = conversationRepository.findById(chatMessageDTO.getConversationId())
        .orElseThrow(() -> ApiException.builder().message("Conversation not found").build());

    User sender = userRepository.findById(chatMessageDTO.getSenderId())
        .orElseThrow(() -> ApiException.builder().message("Sender not found").build());

    ChatMessageEntity message = new ChatMessageEntity();
    message.setSender(sender);
    message.setConversation(conversation);
    message.setContent(chatMessageDTO.getContent());
    message.setTimestamp(LocalDateTime.now());

    messageRepository.save(message);
    // Enviar el mensaje por WebSocket
    // TODO: UNCOMMENT, JUST FOR POSTMAN TEST
    // messagingTemplate.convertAndSend("/topic/conversations/" +
    // conversation.getId(), message);

    return messageMapper.toDto(message);
  }

  @Override
  public List<ChatMessageDTO> getMessages(Long conversationId) {
    ConversationEntity conversation = conversationRepository.findById(conversationId)
        .orElseThrow(() -> ApiException.builder().message("Conversation does not exists").build());
    List<ChatMessageEntity> messages = messageRepository.findByConversationIdOrderByTimestampAsc(conversation.getId());
    return messages.stream()
        .map(messageMapper::toDto)
        .collect(Collectors.toList());
  }
}
