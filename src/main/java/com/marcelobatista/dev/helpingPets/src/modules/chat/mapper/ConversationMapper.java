package com.marcelobatista.dev.helpingPets.src.modules.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ConversationEntity;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ConversationDTO;

@Mapper(componentModel = "spring", uses = ChatMessageMapper.class)
public interface ConversationMapper {

  // ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);

  @Mapping(source = "sender.id", target = "senderId")
  @Mapping(source = "receiver.id", target = "receiverId")
  @Mapping(source = "conversation.id", target = "conversationId")
  ConversationDTO toDto(ConversationEntity conversation);

  @Mapping(target = "sender", ignore = true)
  @Mapping(target = "receiver", ignore = true)
  @Mapping(target = "id", ignore = true)
  ConversationEntity toEntity(ConversationDTO conversationDTO);
}
