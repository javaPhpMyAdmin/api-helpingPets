package com.marcelobatista.dev.helpingPets.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableAsync
public class HelpingPetsApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    SpringApplication.run(HelpingPetsApplication.class, args);
  }

}
