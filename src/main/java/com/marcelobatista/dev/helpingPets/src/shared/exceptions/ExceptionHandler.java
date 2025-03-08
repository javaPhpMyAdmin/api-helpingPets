package com.marcelobatista.dev.helpingPets.src.shared.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marcelobatista.dev.helpingPets.src.shared.utils.HttpErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    List<String> generalErrors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      if (error instanceof FieldError fieldErr) {
        String fieldName = fieldErr.getField();
        String errorMessage = fieldErr.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      } else {
        generalErrors.add(error.getDefaultMessage());
      }
    });

    HttpErrorResponse response = HttpErrorResponse.of("Unprocessable entity", 422, errors, generalErrors);

    return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
  public ResponseEntity<HttpErrorResponse> handleException(ApiException e) {
    log.info("Handling ApiException: {}", e.getMessage());
    var response = HttpErrorResponse.of(e.getMessage(), e.getStatus(), e.getErrors(), null);
    return new ResponseEntity<HttpErrorResponse>(response, HttpStatus.valueOf(e.getStatus()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<HttpErrorResponse> handleException(BadCredentialsException e) {
    log.info("Handling BadCredentialsException: {}", e.getMessage());
    var response = HttpErrorResponse.of(e.getMessage(), 401, null, null);
    return new ResponseEntity<HttpErrorResponse>(response, HttpStatus.valueOf(401));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<HttpErrorResponse> handleException(AuthorizationDeniedException e) {
    log.info("Handling AuthorizationDeniedException: {}", e.getMessage());
    var response = HttpErrorResponse.of(e.getMessage(), 403, null, null);
    return new ResponseEntity<HttpErrorResponse>(response, HttpStatus.valueOf(403));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<HttpErrorResponse> handleException(Exception e) {
    log.error("Unhandled exception", e);
    var response = HttpErrorResponse.of("Unexpected internal error", 500);
    return new ResponseEntity<HttpErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<HttpErrorResponse> handleException(IllegalArgumentException e) {
    log.error("Handling IllegalArgumentException: {}", e);
    var response = HttpErrorResponse.of(e.getMessage(), 400, null, null);
    return new ResponseEntity<HttpErrorResponse>(response,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    // Obtener el nombre del parámetro que causó el error
    String parameterName = ex.getCause().toString(); // Puede ser nulo o vacio dependiendo de la situación.
    String message = "Argument type mismatch: " + ex.getMessage();

    return ResponseEntity.badRequest().body(parameterName.toString());
  }

}
