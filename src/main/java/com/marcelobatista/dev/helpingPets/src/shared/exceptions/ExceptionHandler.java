package com.marcelobatista.dev.helpingPets.src.shared.exceptions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marcelobatista.dev.helpingPets.src.shared.Response.CustomErrorResponse;
import com.marcelobatista.dev.helpingPets.src.shared.utils.RequestUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {
  private final RequestUtils requestUtils;

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

    CustomErrorResponse response = requestUtils.getResponseForError((ServletWebRequest) request, errors,
        "Unprocessable entity",
        HttpStatus.valueOf(422));

    return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
  public ResponseEntity<CustomErrorResponse> handleException(ApiException e, HttpServletRequest request) {
    log.info("Handling ApiException: {}", e.getMessage());

    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        e.getStatus(),
        request.getRequestURI(),
        HttpStatus.valueOf(e.getStatus()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());
    return new ResponseEntity<CustomErrorResponse>(response, HttpStatus.valueOf(e.getStatus()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<CustomErrorResponse> handleException(BadCredentialsException e, HttpServletRequest request) {
    log.info("Handling BadCredentialsException: {}", e.getMessage());
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.UNAUTHORIZED.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.UNAUTHORIZED.value()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());
    return new ResponseEntity<CustomErrorResponse>(response, HttpStatus.UNAUTHORIZED);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<CustomErrorResponse> handleException(AuthorizationDeniedException e,
      HttpServletRequest request) {
    log.info("Handling AuthorizationDeniedException: {}", e.getMessage());
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.FORBIDDEN.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.FORBIDDEN.value()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());
    return new ResponseEntity<CustomErrorResponse>(response, HttpStatus.FORBIDDEN);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorResponse> handleException(Exception e, HttpServletRequest request) {
    log.error("Unhandled exception", e);
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());
    return new ResponseEntity<CustomErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CustomErrorResponse> handleException(IllegalArgumentException e, HttpServletRequest request) {
    log.error("Handling IllegalArgumentException: {}", e);
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.BAD_REQUEST.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());
    return new ResponseEntity<CustomErrorResponse>(response,
        HttpStatus.BAD_REQUEST);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
  public ResponseEntity<CustomErrorResponse> handleIOException(IOException e, HttpServletRequest request) {
    log.error("Handling IOException: {}", e);
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.BAD_GATEWAY.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.BAD_GATEWAY.value()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());

    return new ResponseEntity<CustomErrorResponse>(response,
        HttpStatus.BAD_GATEWAY);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(UncheckedIOException.class)
  public ResponseEntity<CustomErrorResponse> handleIOException(UncheckedIOException e, HttpServletRequest request) {
    log.error("Handling UncheckedIOException: {}", e);
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.BAD_GATEWAY.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.BAD_GATEWAY.value()),
        e.getMessage(),
        e.getClass().getSimpleName(),
        Map.of());

    return new ResponseEntity<CustomErrorResponse>(response,
        HttpStatus.BAD_GATEWAY);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(value = { ExpiredJwtException.class })
  public ResponseEntity<CustomErrorResponse> handleExpiredJwtException(ExpiredJwtException ex,
      HttpServletRequest request) {
    // Custom error message class
    CustomErrorResponse response = new CustomErrorResponse(
        Instant.now().toString(),
        HttpStatus.UNAUTHORIZED.value(),
        request.getRequestURI(),
        HttpStatus.valueOf(HttpStatus.UNAUTHORIZED.value()),
        ex.getMessage(),
        ex.getClass().getSimpleName(),
        Map.of());

    return new ResponseEntity<CustomErrorResponse>(response,
        HttpStatus.UNAUTHORIZED);
  }

}
