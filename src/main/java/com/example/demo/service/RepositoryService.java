package com.example.demo.service;

import com.example.demo.client.GithubClient;
import com.example.demo.client.GithubRepositoryResponse;
import com.example.demo.dto.RepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final GithubClient githubClient;

    public RepositoryDto getRepository(String owner, String repo) {
        GithubRepositoryResponse response = githubClient.getRepository(owner, repo);
        return RepositoryDto.builder()
                .fullName(response.getFullName())
                .description(response.getDescription())
                .cloneUrl(response.getCloneUrl())
                .stars(response.getStars())
                .createdAt(response.getCreatedAt())
                .build();
    }
}