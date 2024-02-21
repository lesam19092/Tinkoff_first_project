package edu.java.scrapper.contoller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})

@WireMockTest

public class GitHubClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("git-hub-base-url", wireMockExtension::baseUrl);
    }

    @Test
    public void fisrttre() {
        wireMockExtension.stubFor(
            WireMock.get("/repos/stukenvitalii/TinkoffBot")
                .willReturn(aResponse().
                    withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withBody("[]").withStatus(200))
        );
        webTestClient.get().uri("/repos/stukenvitalii/TinkoffBot")
            .exchange()
            .expectStatus().isOk()
            .expectBody().json("[]");
    }

}
