package com.marcelobatista.dev.helpingPets.src.shared.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.marcelobatista.dev.helpingPets.src.shared.Response.GlobalResponse;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestUtils {

  public GlobalResponse getResponse(HttpServletRequest request, Map<?, ?> data, String message,
      HttpStatus status) {
    return new GlobalResponse(now().toString(), status.value(), request.getRequestURI(),
        HttpStatus.valueOf(status.value()), message, EMPTY, data);
  }

}
