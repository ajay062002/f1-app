package com.example.f1app.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
    var errors = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.groupingBy(
            e -> e.getField(),
            Collectors.mapping(e -> e.getDefaultMessage(), Collectors.toList())
        ));
    return ResponseEntity.badRequest().body(Map.of(
        "message", "Validation failed",
        "errors", errors
    ));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleUnique(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
        "message", "Username or email already exists"
    ));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleJson(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(Map.of("message", "Malformed JSON request"));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntime(RuntimeException ex) {
    return ResponseEntity.badRequest().body(Map.of(
        "message", ex.getMessage() == null ? "Request failed" : ex.getMessage()
    ));
  }
}
