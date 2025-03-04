package com.marcelobatista.dev.helpingPets.src.modules.chat.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ChatMessageEntity;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
  List<ChatMessageEntity> findByConversationIdOrderByTimestampAsc(Long conversationId);
}
