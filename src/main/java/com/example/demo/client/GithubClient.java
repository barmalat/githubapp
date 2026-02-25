package com.example.demo.client;

import com.example.demo.client.config.ClientConfiguration;
import com.example.demo.client.config.GithubClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", url = "https://api.github.com", configuration = ClientConfiguration.class, fallback = GithubClientFallback.class)
public interface GithubClient {

    @GetMapping("/repos/{owner}/{repository-name}")
    GithubRepositoryResponse getRepository(
            @PathVariable("owner") String owner,
            @PathVariable("repository-name") String repositoryName
    );
}