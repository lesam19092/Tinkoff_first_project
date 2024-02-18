package edu.java.client;

import org.springframework.web.reactive.function.client.WebClient;

public class StackOverFlowClient {


    private final WebClient webClient;

    private final String gitApi = "https://api.github.com/";

    public StackOverFlowClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(gitApi).build();
    }

    public Details someRestCall(String name) {
        return this.webClient.get().uri("/{name}/details", name).retrieve().bodyToMono(Details.class);
    }
}
