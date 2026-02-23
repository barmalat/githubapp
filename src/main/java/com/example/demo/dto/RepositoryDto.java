package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepositoryDto {
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private String createdAt;
}