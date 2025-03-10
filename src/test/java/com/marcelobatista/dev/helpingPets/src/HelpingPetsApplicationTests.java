package com.marcelobatista.dev.helpingPets.src;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
class HelpingPetsApplicationTests {
  @SuppressWarnings("unused")
  @BeforeAll
  public static void setup() {
    // Cargar las variables de entorno para los tests
    Dotenv dotenv = Dotenv.configure()
        .filename(".env.dev")
        .ignoreIfMissing() // No falla si no encuentra el archivo .env
        .load();
  }

  @Test
  void contextLoads() {
  }

}
