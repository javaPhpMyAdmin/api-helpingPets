package com.marcelobatista.dev.helpingPets.src.shared.utils.validators;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, String> {
  private final JdbcClient jdbcClient;
  private String tableName;
  private String columnName;

  public UniqueValidator(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  @Override
  public void initialize(Unique constraintAnnotation) {
    tableName = constraintAnnotation.tableName();
    columnName = constraintAnnotation.columnName();
  }

  @Override
  public boolean isValid(String input, ConstraintValidatorContext context) {
    return jdbcClient.sql("SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?")
        .param(input)
        .query(Integer.class)
        .single() == 0;

  }
}
