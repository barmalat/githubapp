package com.example.demo.controller;

import com.example.demo.model.dto.RepositoryDto;
import com.example.demo.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/local/repositories")
@RequiredArgsConstructor
public class LocalRepositoryController {
    private final RepositoryService repositoryService;

    @GetMapping("/{owner}/{repository-name}")
    public RepositoryDto getRepository(
            @PathVariable String owner,
            @PathVariable("repository-name") String repositoryName) {
        return repositoryService.getLocalRepository(owner, repositoryName);
    }
}