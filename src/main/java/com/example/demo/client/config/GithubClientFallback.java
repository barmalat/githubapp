package com.example.demo.client.config;

import com.example.demo.client.GithubClient;
import com.example.demo.client.GithubRepositoryResponse;
import org.springframework.stereotype.Component;

@Component
public class GithubClientFallback implements GithubClient {
    @Override
    public GithubRepositoryResponse getRepository(String owner, String repositoryName) {
        return new GithubRepositoryResponse();
    }
}