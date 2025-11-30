package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
            errors.put(error.getField(), error.getDefaultMessage()));
        
            return ResponseEntity.badRequest().body(errors.toString());
        
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleContraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error ->
            errors.put(error.getPropertyPath().toString(), error.getMessage()));
        
        return ResponseEntity.badRequest().body(errors.toString());
    }

    // 3. Your own business exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBusinessError(IllegalArgumentException ex) {
        log.warn("Business rule violation: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 4. Catch-all (optional – never let 500 with stack trace leak)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleEverything(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(500).body("Internal server error");
    }

}
