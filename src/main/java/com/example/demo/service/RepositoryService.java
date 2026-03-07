package com.example.demo.service;

import com.example.demo.client.GithubClient;
import com.example.demo.client.GithubRepositoryResponse;
import com.example.demo.exception.LocalRepositoryNotFoundException;
import com.example.demo.mappers.RepositoryMapper;
import com.example.demo.model.dto.RepositoryDto;
import com.example.demo.model.entities.LocalRepository;
import com.example.demo.repository.LocalRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final GithubClient githubClient;
    private final LocalRepositoryRepository repositoryRepository;
    private final RepositoryMapper repositoryMapper;

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

    public RepositoryDto saveRepository(String owner, String repositoryName) {
        GithubRepositoryResponse response = githubClient.getRepository(owner, repositoryName);
        LocalRepository repository = LocalRepository.builder()
                .fullName(response.getFullName())
                .description(response.getDescription())
                .cloneUrl(response.getCloneUrl())
                .stars(response.getStars())
                .createdAt(response.getCreatedAt())
                .build();
        repositoryRepository.save(repository);
        return repositoryMapper.toDto(repository);
    }

    public RepositoryDto getLocalRepository(String owner, String repositoryName) {
        String fullName = owner + "/" + repositoryName;
        LocalRepository repository = repositoryRepository.findFirstByFullName(fullName)
                .orElseThrow(() -> new LocalRepositoryNotFoundException("Nie znaleziono wskazanego repozytorium"));
        return repositoryMapper.toDto(repository);
    }

    public RepositoryDto updateRepository(String owner, String repositoryName) {
        String fullName = owner + "/" + repositoryName;
        LocalRepository repository = repositoryRepository.findFirstByFullName(fullName)
                .orElseThrow(() -> new LocalRepositoryNotFoundException("Nie znaleziono wskazanego repozytorium"));
        Long id = repository.getId();
        GithubRepositoryResponse response = githubClient.getRepository(owner, repositoryName);
        repository = repositoryMapper.toEntity(response);
        repository.setId(id);//metoda update w encji
        repositoryRepository.save(repository);
        return repositoryMapper.toDto(repository);
    }

    public RepositoryDto deleteRepository(String owner, String repositoryName) {
        String fullName = owner + "/" + repositoryName;
        LocalRepository repository = repositoryRepository.findFirstByFullName(fullName)
                .orElseThrow(() -> new LocalRepositoryNotFoundException("Nie znaleziono wskazanego repozytorium"));
        repositoryRepository.delete(repository);
        return repositoryMapper.toDto(repository);
    }
}