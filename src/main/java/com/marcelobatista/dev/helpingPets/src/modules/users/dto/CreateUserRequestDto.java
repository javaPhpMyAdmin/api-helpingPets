package com.marcelobatista.dev.helpingPets.src.modules.users.dto;

import org.hibernate.validator.constraints.Length;

import com.marcelobatista.dev.helpingPets.src.shared.utils.validators.Unique;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequestDto {

  @Email
  @Unique(columnName = "email", tableName = "users", message = "User with this email already exists")
  private String email;
  @NotNull
  @Length(min = 8)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "must contain at least one uppercase letter, one lowercase letter, and one digit.")
  private String password;
  private String passwordConfirmation;
  @Nullable
  private String firstName;
  @Nullable
  private String lastName;
}
