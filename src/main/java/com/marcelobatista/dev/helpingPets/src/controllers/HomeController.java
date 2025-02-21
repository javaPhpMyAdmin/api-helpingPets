package com.marcelobatista.dev.helpingPets.src.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  @RequestMapping("/")
  public String home(Principal principal) {

    return "Welcome to the Helping Pets API!";
  }

}
