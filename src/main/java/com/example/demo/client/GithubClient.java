package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", url = "https://api.github.com")
public interface GithubClient {

    @GetMapping("/repos/{owner}/{repository-name}")
    GithubRepositoryResponse getRepository(
            @PathVariable("owner") String owner,
            @PathVariable("repository-name") String repositoryName
    );
}
