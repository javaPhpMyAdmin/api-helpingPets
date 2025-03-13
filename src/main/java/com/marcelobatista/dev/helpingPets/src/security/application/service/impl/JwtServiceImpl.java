package com.marcelobatista.dev.helpingPets.src.security.application.service.impl;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.application.service.JwtService;
import com.marcelobatista.dev.helpingPets.src.security.domain.Token;

@Service
public class JwtServiceImpl implements JwtService {

  @Override
  public String createToken(User user, Function<Token, String> tokenFunction) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createToken'");
  }

}
