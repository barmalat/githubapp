package com.example.demo.mappers;

import com.example.demo.client.GithubRepositoryResponse;
import com.example.demo.model.dto.RepositoryDto;
import com.example.demo.model.entities.LocalRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {
    RepositoryDto toDto(LocalRepository repository);
    LocalRepository toEntity(GithubRepositoryResponse response);
}