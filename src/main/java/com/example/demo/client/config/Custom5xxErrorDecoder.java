package com.example.demo.client.config;

import com.example.demo.exception.GithubException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class Custom5xxErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        if (exception.status() == 503) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    50L,
                    response.request());
        }
        if (exception.status() == 500) {
            return new GithubException(exception.getMessage(), exception.status());
        }
        return exception;
    }
}