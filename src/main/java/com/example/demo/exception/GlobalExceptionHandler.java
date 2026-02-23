package com.example.demo.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Repository not found");
    }
}