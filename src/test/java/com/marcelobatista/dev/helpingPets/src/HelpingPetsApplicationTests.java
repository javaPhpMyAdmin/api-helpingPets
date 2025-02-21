package com.marcelobatista.dev.helpingPets.src;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
class HelpingPetsApplicationTests {
  @BeforeAll
  public static void setup() {
    // Cargar las variables de entorno para los tests
    Dotenv dotenv = Dotenv.load();
  }

  @Test
  void contextLoads() {
  }

}
