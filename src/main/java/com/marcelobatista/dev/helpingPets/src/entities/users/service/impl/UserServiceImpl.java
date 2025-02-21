package com.marcelobatista.dev.helpingPets.src.entities.users.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.auth.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.entities.users.User;
import com.marcelobatista.dev.helpingPets.src.entities.users.VerificationCode;
import com.marcelobatista.dev.helpingPets.src.entities.users.data.CreateUserRequest;
import com.marcelobatista.dev.helpingPets.src.entities.users.data.UpdateUserRequest;
import com.marcelobatista.dev.helpingPets.src.entities.users.data.UserResponse;
import com.marcelobatista.dev.helpingPets.src.entities.users.repository.UserRepository;
import com.marcelobatista.dev.helpingPets.src.entities.users.repository.VerificationCodeRepository;
import com.marcelobatista.dev.helpingPets.src.entities.users.service.UserService;
import com.marcelobatista.dev.helpingPets.src.enumeration.EventType;
import com.marcelobatista.dev.helpingPets.src.events.user.UserEvent;
import com.marcelobatista.dev.helpingPets.src.shared.exceptions.ApiException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final VerificationCodeRepository verificationCodeRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  @Override
  public UserResponse create(@Valid CreateUserRequest userRequest) {
    var user = new User(userRequest);
    user = userRepository.save(user);
    sendVerificationEmail(user);
    return new UserResponse(user);

  }

  private void sendVerificationEmail(User user) {
    VerificationCode verificationCode = new VerificationCode(user);
    user.setVerificationCode(verificationCode);
    verificationCodeRepository.save(verificationCode);
    publisher.publishEvent(new UserEvent(user, EventType.REGISTRATION, Map.of("key", verificationCode.getCode())));
    // SendWelcomeEmailJob sendWelcomEmailJob = new
    // SendWelcomeEmailJob(user.getId());
    // BackgroundJobRequest.enqueue(sendWelcomEmailJob);
  }

  @Transactional
  @Override
  public UserResponse updateUser(@Valid UpdateUserRequest userRequest) {
    User user = SecurityUtil.getAuthenticatedUser();
    log.info("User: {}", user);
    user = userRepository.getReferenceById(user.getId());
    user.update(userRequest);
    user = userRepository.save(user);
    return new UserResponse(user);
  }

  @Override
  public void verifyAccount(String token) {
    VerificationCode verificationCode = verificationCodeRepository.findByCode(token)
        .orElseThrow(() -> ApiException.builder().status(400).message("The verification code was invalid").build());

    var user = verificationCode.getUser();
    user.setVerified(true);
    userRepository.save(user);
    verificationCodeRepository.delete(verificationCode);
  }

}
