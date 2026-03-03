package com.example.demo.controller;

import com.example.demo.client.GithubRepositoryResponse;
import com.example.demo.model.dto.RepositoryDto;
import com.example.demo.model.entities.LocalRepository;
import com.example.demo.repository.LocalRepositoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8111)
public class RepositoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WireMockServer wireMockServer;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private LocalRepositoryRepository localRepositoryRepository;

    @Test
    void getRepository_DataCorrect_RepositoryDtoReturn() throws Exception {
        //given
        GithubRepositoryResponse response = GithubRepositoryResponse.builder()
                .fullName("owner/name")
                .stars(123)
                .description("desc")
                .cloneUrl("cloneUrl")
                .createdAt("now")
                .build();
        wireMockServer.stubFor(get("/repos/owner/name").willReturn(
                aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)
        ));
        RepositoryDto repositoryDto = RepositoryDto.builder()
                .id(null)
                .fullName("owner/name")
                .description("desc")
                .cloneUrl("cloneUrl")
                .stars(123)
                .createdAt("now")
                .build();
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner/name"))
                .andExpect(jsonPath("$.id").value(repositoryDto.getId()))
                .andExpect(jsonPath("$.fullName").value(repositoryDto.getFullName()))
                .andExpect(jsonPath("$.description").value(repositoryDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(repositoryDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(repositoryDto.getStars()))
                .andExpect(jsonPath("$.createdAt").value(repositoryDto.getCreatedAt()));
    }

    @Test
    void saveRepository_DataCorrect_RepositoryDtoReturn() throws Exception {
        //given
        GithubRepositoryResponse response = GithubRepositoryResponse.builder()
                .fullName("owner/name")
                .stars(123)
                .description("desc")
                .cloneUrl("cloneUrl")
                .createdAt("now")
                .build();
        wireMockServer.stubFor(get("/repos/owner/name").willReturn(
                aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)
        ));
        RepositoryDto repositoryDto = RepositoryDto.builder()
                .id(1L)
                .fullName("owner/name")
                .description("desc")
                .cloneUrl("cloneUrl")
                .stars(123)
                .createdAt("now")
                .build();
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/owner/name"))
                .andExpect(jsonPath("$.id").value(repositoryDto.getId()))
                .andExpect(jsonPath("$.fullName").value(repositoryDto.getFullName()))
                .andExpect(jsonPath("$.description").value(repositoryDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(repositoryDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(repositoryDto.getStars()))
                .andExpect(jsonPath("$.createdAt").value(repositoryDto.getCreatedAt()));

        assertTrue(localRepositoryRepository.findFirstByFullName(repositoryDto.getFullName()).isPresent());
    }

    @Test
    void updateRepository_DataCorrect_RepositoryDtoReturn() throws Exception {
        //given
        LocalRepository repository = LocalRepository.builder()
                .id(null)
                .fullName("owner/name")
                .description("test")
                .cloneUrl("test")
                .stars(100)
                .createdAt("test")
                .build();
        localRepositoryRepository.save(repository);

        GithubRepositoryResponse response = GithubRepositoryResponse.builder()
                .fullName("owner/name")
                .stars(123)
                .description("desc")
                .cloneUrl("cloneUrl")
                .createdAt("now")
                .build();
        wireMockServer.stubFor(get("/repos/owner/name").willReturn(
                aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)
        ));
        RepositoryDto repositoryDto = RepositoryDto.builder()
                .id(1L)
                .fullName("owner/name")
                .description("desc")
                .cloneUrl("cloneUrl")
                .stars(123)
                .createdAt("now")
                .build();
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/owner/name"))
                .andExpect(jsonPath("$.id").value(repositoryDto.getId()))
                .andExpect(jsonPath("$.fullName").value(repositoryDto.getFullName()))
                .andExpect(jsonPath("$.description").value(repositoryDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(repositoryDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(repositoryDto.getStars()))
                .andExpect(jsonPath("$.createdAt").value(repositoryDto.getCreatedAt()));

        assertTrue(localRepositoryRepository.findFirstByFullName(repositoryDto.getFullName()).isPresent());
    }

    @Test
    void deleteRepository_DataCorrect_RepositoryDtoReturn() throws Exception {
        //given
        LocalRepository repository = LocalRepository.builder()
                .id(null)
                .fullName("owner/name")
                .description("desc")
                .cloneUrl("cloneUrl")
                .stars(123)
                .createdAt("now")
                .build();
        localRepositoryRepository.save(repository);

        RepositoryDto repositoryDto = RepositoryDto.builder()
                .id(1L)
                .fullName("owner/name")
                .description("desc")
                .cloneUrl("cloneUrl")
                .stars(123)
                .createdAt("now")
                .build();
        //when+then
        mockMvc.perform(MockMvcRequestBuilders.delete("/repositories/owner/name"))
                .andExpect(jsonPath("$.id").value(repositoryDto.getId()))
                .andExpect(jsonPath("$.fullName").value(repositoryDto.getFullName()))
                .andExpect(jsonPath("$.description").value(repositoryDto.getDescription()))
                .andExpect(jsonPath("$.cloneUrl").value(repositoryDto.getCloneUrl()))
                .andExpect(jsonPath("$.stars").value(repositoryDto.getStars()))
                .andExpect(jsonPath("$.createdAt").value(repositoryDto.getCreatedAt()));

        assertFalse(localRepositoryRepository.findFirstByFullName(repositoryDto.getFullName()).isPresent());
    }
}