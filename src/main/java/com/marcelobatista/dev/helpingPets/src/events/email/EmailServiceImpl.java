package com.marcelobatista.dev.helpingPets.src.events.email;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;
import com.marcelobatista.dev.helpingPets.src.shared.utils.EmailUtils.EmailUtils;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
  private static final String UTF_8_ENCODING = "UTF-8";
  private static final String EMAILTEMPLATE = "emailtemplate";
  private static final String PASSWORD_RESTET_REQUEST = "New Reset Password Request";
  private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
  private final EmailUtils emailUtils;
  private final JavaMailSender sender;
  private final TemplateEngine templateEngine;

  @Value("${app.base-url}")
  private String host;
  @Value("${spring.mail.username}")
  private String fromEmail;

  @Override
  @Async
  public void sendNewAccountEmail(String name, String email, String token) {
    try {
      Context context = new Context();
      context.setVariables(Map.of("name", name, "url",
          emailUtils.getVerificationAccountUrl(host, token)));
      String text = templateEngine.process(EMAILTEMPLATE, context);
      MimeMessage message = getMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
      helper.setPriority(1);
      helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
      helper.setFrom(fromEmail);
      helper.setTo(email);
      helper.setText(text, true);
      sender.send(message);
    } catch (Exception e) {
      log.error("Error sending email: " + e.getMessage());
      Map<String, String> errors = Collections.emptyMap();
      throw ApiException.builder().message("Error sending email").errors(errors).build();
    }
  }

  private MimeMessage getMimeMessage() {
    return sender.createMimeMessage();
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
