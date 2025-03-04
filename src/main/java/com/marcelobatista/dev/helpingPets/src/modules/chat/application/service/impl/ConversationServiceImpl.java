package com.marcelobatista.dev.helpingPets.src.modules.chat.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.chat.application.service.ConversationService;
import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ConversationEntity;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ConversationDTO;
import com.marcelobatista.dev.helpingPets.src.modules.chat.infrastructure.ConversationRepository;
import com.marcelobatista.dev.helpingPets.src.modules.chat.mapper.ConversationMapper;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

  private final ConversationRepository conversationRepository;
  private final UserRepository userRepository;
  private final ConversationMapper conversationMapper;

  @Override
  @Transactional
  public ConversationDTO startConversation(ConversationDTO conversationDTO) {
    Optional<ConversationEntity> existingConversation = conversationRepository
        .findBySenderIdAndReceiverId(conversationDTO.getSenderId(), conversationDTO.getReceiverId());
    if (existingConversation.isPresent()) {
      return conversationMapper.toDto(existingConversation.get());
    }

    User sender = userRepository.findById(conversationDTO.getSenderId())
        .orElseThrow(() -> ApiException.builder().message("Sender not found").build());
    User receiver = userRepository.findById(conversationDTO.getReceiverId())
        .orElseThrow(() -> ApiException.builder().message("Receiver not found").build());

    ConversationEntity conversation = new ConversationEntity();
    conversation.setSender(sender);
    conversation.setReceiver(receiver);

    conversationRepository.save(conversation);

    return conversationMapper.toDto(conversation);
  }

  @Override
  @Transactional
  public Optional<ConversationDTO> findConversation(ConversationDTO conversationDTO) {
    Optional<ConversationEntity> conversation = conversationRepository.findByParticipants(
        conversationDTO.getSenderId(), conversationDTO.getReceiverId());

    return conversation.map(conversationMapper::toDto);
  }

  @Override
  @Transactional
  public List<ConversationDTO> getUserConversations(Long userId) {
    User userForConversations = userRepository.findById(userId)
        .orElseThrow(() -> ApiException.builder().message("User does not exist").build());
    List<ConversationEntity> conversations = conversationRepository
        .findBySenderIdOrReceiverId(userForConversations.getId(), userForConversations.getId());
    return conversations.stream()
        .map(conversationMapper::toDto)
        .collect(Collectors.toList());
  }

}
