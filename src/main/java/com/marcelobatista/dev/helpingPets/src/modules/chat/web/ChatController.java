package com.marcelobatista.dev.helpingPets.src.modules.chat.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.modules.chat.application.service.ChatMessageService;
import com.marcelobatista.dev.helpingPets.src.modules.chat.application.service.ConversationService;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ChatMessageDTO;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.ConversationDTO;
import com.marcelobatista.dev.helpingPets.src.modules.chat.dto.TypingDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Tag(name = "Conversations", description = "Conversations managment")
@Slf4j
public class ChatController {
  private final ChatMessageService messageService;
  private final SimpMessagingTemplate messagingTemplate;
  private final ConversationService conversationService;

  @PostMapping("/send-message")
  public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
    ChatMessageDTO message = messageService.sendMessage(chatMessageDTO);
    return ResponseEntity.ok(message);
  }

  @PostMapping("/start-conversation")
  public ResponseEntity<ConversationDTO> startConversation(@RequestBody ConversationDTO conversationDTO) {
    ConversationDTO conversation = conversationService.startConversation(conversationDTO);
    return ResponseEntity.ok(conversation);
  }

  @GetMapping("/conversation/{conversationId}")
  public ResponseEntity<List<ChatMessageDTO>> getMessagesByConversation(@PathVariable Long conversationId) {
    List<ChatMessageDTO> messages = messageService.getMessages(conversationId);
    return ResponseEntity.ok(messages);
  }

  @MessageMapping("/typing")
  public void typing(@Payload TypingDTO typingDTO) {
    messagingTemplate.convertAndSend("/topic/conversations/" + typingDTO.getConversationId() + "/typing", typingDTO);
  }

  @GetMapping("/messages/{conversationId}")
  public ResponseEntity<List<ChatMessageDTO>> getAllMessages(@PathVariable Long conversationId) {
    return ResponseEntity.ok().body(messageService.getMessages(conversationId));
  }

  @GetMapping("/user-conversations/{userId}")
  public ResponseEntity<List<ConversationDTO>> getConversationsByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok().body(conversationService.getUserConversations(userId));
  }

}
