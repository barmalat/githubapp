package com.example.demo.controller;

import com.example.demo.model.dto.RepositoryDto;
import com.example.demo.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repositories")
@RequiredArgsConstructor
public class RepositoryController {
    private final RepositoryService repositoryService;

    @GetMapping("/{owner}/{repository-name}")
    public RepositoryDto getRepository(
            @PathVariable String owner,
            @PathVariable("repository-name") String repositoryName) {
        return repositoryService.getRepository(owner, repositoryName);
    }

    @PostMapping("/{owner}/{repository-name}")
    public RepositoryDto saveRepository(
            @PathVariable String owner,
            @PathVariable("repository-name") String repositoryName) {
        return repositoryService.saveRepository(owner, repositoryName);
    }

    @PutMapping("/{owner}/{repository-name}")
    public RepositoryDto updateRepository(
            @PathVariable String owner,
            @PathVariable("repository-name") String repositoryName) {
        return repositoryService.updateRepository(owner, repositoryName);
    }

    @DeleteMapping("/{owner}/{repository-name}")
    public RepositoryDto deleteRepository(
            @PathVariable String owner,
            @PathVariable("repository-name") String repositoryName) {
        return repositoryService.deleteRepository(owner, repositoryName);
    }
}