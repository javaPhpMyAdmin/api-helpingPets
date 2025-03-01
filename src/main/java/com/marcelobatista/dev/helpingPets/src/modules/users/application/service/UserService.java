package com.marcelobatista.dev.helpingPets.src.modules.users.application.service;

import com.marcelobatista.dev.helpingPets.src.modules.users.dto.CreateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UpdateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UserResponse;

public interface UserService {
  public UserResponse create(CreateUserRequestDto userRequest);

  public UserResponse updateUser(UpdateUserRequestDto userRequest);

  public void verifyAccount(String token);

}
