package com.marcelobatista.dev.helpingPets.src.modules.chat.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.chat.domain.ConversationEntity;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
  @Query("SELECT c FROM ConversationEntity c WHERE (c.sender.id = :firstUserId AND c.receiver.id = :secondUserId) OR (c.sender.id = :secondUserId AND c.receiver.id = :firstUserId)")
  Optional<ConversationEntity> findByParticipants(@Param("firstUserId") Long firstUserId,
      @Param("secondUserId") Long secondUserId);

  List<ConversationEntity> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

  Optional<ConversationEntity> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

  @SuppressWarnings("null")
  Optional<ConversationEntity> findById(Long conversationId);

}
