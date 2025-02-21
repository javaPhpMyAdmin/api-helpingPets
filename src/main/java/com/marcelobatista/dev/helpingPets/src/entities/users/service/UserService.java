package com.marcelobatista.dev.helpingPets.src.entities.users.service;

import com.marcelobatista.dev.helpingPets.src.entities.users.data.CreateUserRequest;
import com.marcelobatista.dev.helpingPets.src.entities.users.data.UpdateUserRequest;
import com.marcelobatista.dev.helpingPets.src.entities.users.data.UserResponse;

public interface UserService {
  public UserResponse create(CreateUserRequest userRequest);

  public UserResponse updateUser(UpdateUserRequest userRequest);

  public void verifyAccount(String token);

}
