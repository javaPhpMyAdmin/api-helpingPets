package com.marcelobatista.dev.helpingPets.src.modules.users.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.marcelobatista.dev.helpingPets.src.modules.users.application.service.UserService;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.CreateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UpdateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UserResponse;
import com.marcelobatista.dev.helpingPets.src.shared.Response.GlobalResponse;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;
  private final RequestUtils requestUtils;

  @PostMapping("/create-user")
  public ResponseEntity<GlobalResponse> createUser(@Valid @RequestBody CreateUserRequestDto userRequest,
      HttpServletRequest request) {
    UserResponse user = userService.create(userRequest);
    return ResponseEntity.created(getUri()).body(requestUtils.getResponse(request, Map.of("userCreated", user),
        "Account created. Check your email to enable your account", HttpStatus.CREATED));
  }

  // create update user endpoint
  @PutMapping("/update-user/{id}")
  public ResponseEntity<GlobalResponse> updateUser(@Valid @RequestBody UpdateUserRequestDto userRequest,
      HttpServletRequest request) {
    UserResponse user = userService.updateUser(userRequest);
    // return ResponseEntity.ok(user);
    return ResponseEntity.ok().body(requestUtils.getResponse(request, Map.of("userUpdated", user),
        "Account created. Check your email to enable your account", HttpStatus.OK));
  }

  @GetMapping("/verify/account")
  public ResponseEntity<GlobalResponse> verifyAccount(@PathParam("token") String token, HttpServletRequest request) {
    userService.verifyAccount(token);
    return ResponseEntity.created(getUri())
        .body(requestUtils.getResponse(request, Map.of("userAccountVerified", "ACCOUNT_VERIFIED_SUCCESFULLY"),
            "The account was correctly verified", HttpStatus.OK));
  }

  private URI getUri() {
    return URI.create("");
  }

}
