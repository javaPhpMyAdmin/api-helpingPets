package com.marcelobatista.dev.helpingPets.src.modules.users.application.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.events.domain.UserEvent;
import com.marcelobatista.dev.helpingPets.src.modules.users.application.service.UserService;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.VerificationCode;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.CreateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UpdateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UserResponse;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.VerificationCodeRepository;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.enums.EventType;
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
  public UserResponse create(@Valid CreateUserRequestDto userRequest) {
    var user = new User(userRequest);
    user = userRepository.save(user);
    sendVerificationEmail(user);
    return new UserResponse(user);

  }

  private void sendVerificationEmail(User user) {
    VerificationCode verificationCode = new VerificationCode(user);
    user.setVerificationCode(verificationCode);
    publisher.publishEvent(new UserEvent(user, EventType.REGISTRATION, Map.of("key", verificationCode.getCode())));
    verificationCode.setEmailSent(true);
    verificationCodeRepository.save(verificationCode);

    // SendWelcomeEmailJob sendWelcomEmailJob = new
    // SendWelcomeEmailJob(user.getId());
    // BackgroundJobRequest.enqueue(sendWelcomEmailJob);
  }

  @Transactional
  @Override
  public UserResponse updateUser(@Valid UpdateUserRequestDto userRequestDto) {
    User currentUser = Optional.ofNullable(SecurityUtil.getAuthenticatedUser())
        .orElseThrow(() -> ApiException.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("User must be authenticated")
            .build());

    currentUser.updateFromDto(userRequestDto);

    return new UserResponse(currentUser);
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

  @Override
  @Transactional
  public List<UserResponse> getUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(user -> new UserResponse(user)).collect(Collectors.toList());

  }

}
