package com.diffindo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Map;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();

        if (ex.getMessage().contains("primary key violation")) {
            response.put("error", "This email address or phone number is already in use.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        response.put("error", "Data integrity violation. Please check your input.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}