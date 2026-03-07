package com.example.demo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        wireMockServer.stubFor(get("/repos/owner/name").willReturn(
                aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))
                        .withStatus(200)
        ));
        //when
        var result = githubClient.getRepository("owner", "name");
        //then
        Assertions.assertAll(
                () -> assertEquals("owner/name", result.getFullName()),
                () -> assertEquals(123, result.getStars()),
                () -> assertEquals("desc", result.getDescription()),
                () -> assertEquals("cloneUrl", result.getCloneUrl()),
                () -> assertEquals("now", result.getCreatedAt())
        );
    }
}