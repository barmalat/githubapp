package com.example.demo.exception;

public class GithubException extends RuntimeException {
    int status;

    public GithubException(String message, int status) {
        super(message);
        this.status = status;
    }
}
