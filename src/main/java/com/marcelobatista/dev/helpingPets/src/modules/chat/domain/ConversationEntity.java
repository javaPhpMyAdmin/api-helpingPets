package com.marcelobatista.dev.helpingPets.src.modules.chat.domain;

import java.util.List;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "conversations")
public class ConversationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id", nullable = false)
  private User receiver;

  @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChatMessageEntity> messages;

  public ConversationEntity(User sender, User receiver) {
    this.sender = sender;
    this.receiver = receiver;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public void setReceiver(User receiver) {
    this.receiver = receiver;
  }

}
