package com.marcelobatista.dev.helpingPets.src.service.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.service.EmailService;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;
import com.marcelobatista.dev.helpingPets.src.shared.utils.EmailUtils.EmailUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
  private static final String PASSWORD_RESTET_REQUEST = "New Reset Password Request";
  private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
  private final EmailUtils emailUtils;
  private final JavaMailSender sender;

  @Value("${app.base-url}")
  private String host;
  @Value("${spring.mail.username}")
  private String fromEmail;

  @Override
  @Async
  public void sendNewAccountEmail(String name, String email, String token) {
    try {
      var message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(email);
      message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
      message.setText(emailUtils.getEmailMessage(name, host, token));
      sender.send(message);
    } catch (Exception e) {
      log.error("Error sending email: " + e.getMessage());
      Map<String, String> errors = Collections.emptyMap();
      throw ApiException.builder().message("Error sending emaild").errors(errors).build();
    }
  }

  @Override
  @Async
  public void sendResetPasswordEmail(String name, String email, String token) {
    try {
      var message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(email);
      message.setSubject(PASSWORD_RESTET_REQUEST);
      message.setText(emailUtils.getResetPasswordMessage(name, host, token));
      sender.send(message);
    } catch (Exception e) {
      log.error("Error sending email: " + e.getMessage());
      Map<String, String> errors = Collections.emptyMap();
      throw ApiException.builder().message("Error sending emaild").errors(errors).build();
    }
  }

}
