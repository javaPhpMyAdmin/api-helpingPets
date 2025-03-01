package com.marcelobatista.dev.helpingPets.src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan
public class HelpingPetsApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure()
        .filename(".env.dev")
        .ignoreIfMissing() // No falla si no encuentra el archivo .env
        .load();
    SpringApplication.run(HelpingPetsApplication.class, args);
  }

}
