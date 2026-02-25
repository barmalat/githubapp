package com.example.demo.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessageDto> handleFeignException(FeignException e) {
        return ResponseEntity
                .status(e.status())
                .body(new ErrorMessageDto(e.getMessage(), e.status()));
    }

    @ExceptionHandler(GithubException.class)
    public ResponseEntity<ErrorMessageDto> handleGithubException(GithubException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorMessageDto(e.getMessage(), e.getStatus()));
    }
}