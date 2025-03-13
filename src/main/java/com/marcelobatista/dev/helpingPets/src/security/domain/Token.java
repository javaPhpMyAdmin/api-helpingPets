package com.marcelobatista.dev.helpingPets.src.security.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Token {
  String access;
  String refresh;
}
