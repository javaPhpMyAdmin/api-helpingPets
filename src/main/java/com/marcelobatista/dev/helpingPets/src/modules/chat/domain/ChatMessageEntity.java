package com.marcelobatista.dev.helpingPets.src.modules.chat.domain;

import java.time.LocalDateTime;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "messages")
public class ChatMessageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "conversation_id", nullable = false)
  private ConversationEntity conversation;

  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private LocalDateTime timestamp;

}
