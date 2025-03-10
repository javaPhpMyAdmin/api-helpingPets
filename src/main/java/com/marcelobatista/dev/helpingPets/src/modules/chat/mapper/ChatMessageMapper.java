package com.marcelobatista.dev.helpingPets.src.modules.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ChatMessageEntity;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ChatMessageDTO;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
  // ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

  @Mapping(source = "conversation.id", target = "conversationId")
  @Mapping(source = "sender.id", target = "senderId")
  ChatMessageDTO toDto(ChatMessageEntity message);

  @Mapping(source = "conversationId", target = "conversation.id")
  @Mapping(target = "sender", ignore = true) // Ignorar sender
  @Mapping(target = "id", ignore = true) // Ignorar id porque JPA lo genera
  @Mapping(target = "timestamp", ignore = true) // Ignorar timestamp porque se asigna en el servicio
  ChatMessageEntity toEntity(ChatMessageDTO dto);
}
