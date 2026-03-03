package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class LocalRepositoryNotFoundException extends LocalRepositoryException {
    public LocalRepositoryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
