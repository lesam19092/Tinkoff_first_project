package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@WireMockTest
public class GitHubClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension
        .newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("git-hub-base-url", wireMockExtension::baseUrl);
    }


    @Test
    public void testStatusCodePositive() {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/lesam19092/Tinkoff_first_project"
            )
            .willReturn(aResponse()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/repos/lesam19092/Tinkoff_first_project")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void testGetValidJson() {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/lesam19092/Tinkoff_first_project"
            )
            .willReturn(aResponse()
                .withBody("{\"id\":752770328,\"name\":\"Tinkoff_first_project\",\"defaultBranch\":null}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/repos/lesam19092/Tinkoff_first_project")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody().json("{\"id\":752770328,\"name\":\"Tinkoff_first_project\",\"defaultBranch\":null}");
    }

    @Test
    public void testIncorrectUrl() {
        wireMockExtension.stubFor(WireMock.get(
                "/repos/lesam19092/Tinkoff_first_project"
            )
            .willReturn(aResponse()
                .withBody("{}")
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)));

        webTestClient.get().uri("/repos/lesam19092/Tinkoff_first_project1")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }
}
