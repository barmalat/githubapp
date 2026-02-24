package com.example.demo.client.config;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new Custom5xxErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                100L,
                1000L,
                3
        );
    }
}