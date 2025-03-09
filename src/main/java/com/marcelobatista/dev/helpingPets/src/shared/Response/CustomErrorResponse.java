package com.marcelobatista.dev.helpingPets.src.shared.Response;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record CustomErrorResponse(String time, int code, String path, HttpStatus status, String message,
    String exception, Map<?, ?> errors) {

}
