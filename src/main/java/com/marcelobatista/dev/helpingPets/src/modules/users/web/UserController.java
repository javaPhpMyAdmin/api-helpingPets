package com.marcelobatista.dev.helpingPets.src.modules.users.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelobatista.dev.helpingPets.src.modules.users.application.service.UserService;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.CreateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UpdateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UserResponse;
import com.marcelobatista.dev.helpingPets.src.security.infrastructure.SecurityUtil;
import com.marcelobatista.dev.helpingPets.src.shared.Response.GlobalResponse;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;
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
@Tag(name = "Users", description = "Users managment")
@Slf4j
public class UserController {

  private final UserService userService;
  private final RequestUtils requestUtils;

  @Operation(summary = "Create an user for the application", description = "Add an user to the database")
  @PostMapping("/create-user")
  public ResponseEntity<GlobalResponse> createUser(@Valid @RequestBody CreateUserRequestDto userRequest,
      HttpServletRequest request) {
    UserResponse user = userService.create(userRequest);
    return ResponseEntity.created(getUri()).body(requestUtils.getResponse(request, Map.of("userCreated", user),
        "Account created. Check your email to enable your account", HttpStatus.CREATED));
  }

  // create update user endpoint
  @SecurityRequirements({
      @SecurityRequirement(name = "oauth2"),
      @SecurityRequirement(name = "cookieAuth")
  })
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

  @GetMapping("/me")
  public ResponseEntity<GlobalResponse> getUserInfo(
      HttpServletRequest request) {
    var user = SecurityUtil.getAuthenticatedUser();
    return ResponseEntity.ok()
        .body(requestUtils.getResponse(request, Map.of("user", user),
            "The user information was correctly retrieved", HttpStatus.OK));
  }

  @GetMapping("/get-all")
  public ResponseEntity<GlobalResponse> getUsers(@PathParam("token") String token, HttpServletRequest request) {
    List<UserResponse> usersRetrieved = userService.getUsers();
    return ResponseEntity.created(getUri())
        .body(requestUtils.getResponse(request, Map.of("users", usersRetrieved),
            "Users in BD", HttpStatus.OK));
  }

  private URI getUri() {
    return URI.create("");
  }

}
