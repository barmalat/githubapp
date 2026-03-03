package com.example.demo.repository;

import com.example.demo.model.entities.LocalRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalRepositoryRepository extends JpaRepository<LocalRepository, Long> {
    Optional<LocalRepository> findFirstByFullName(String fullName);
}