package com.marcelobatista.dev.helpingPets.src.security.application.service;

import java.util.function.Function;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.domain.Token;

public interface JwtService {
  String createToken(User user, Function<Token, String> tokenFunction);
}
