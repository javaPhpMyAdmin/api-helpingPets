package com.marcelobatista.dev.helpingPets.src.shared.utils.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Constraint(validatedBy = UniqueValidator.class)
@Target({ ElementType.FIELD })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {
  String message() default "Field must be unique";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};

  String columnName();

  String tableName();
}
