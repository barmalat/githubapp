package com.example.demo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@AutoConfigureWireMock(port = 8111)
@SpringBootTest
public class GithubClientTest {
    @Autowired
    WireMockServer wireMockServer;
    @Autowired
    GithubClient githubClient;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldGetRepository() throws JsonProcessingException {
        //given
        GithubRepositoryResponse response = GithubRepositoryResponse.builder()
                .fullName("owner/name")
                .stars(123)
                .description("desc")
                .cloneUrl("cloneUrl")
                .createdAt("now")
                .build();
    }
}