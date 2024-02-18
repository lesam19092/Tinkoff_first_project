package edu.java.client;

import org.springframework.web.reactive.function.client.WebClient;

public class StackOverFlowClient {


    private final String stackOverFlowApi = "https://api.stackexchange.com/2.2" ; //TODO найти нормальый апи
    private final WebClient webClient;

    public StackOverFlowClient() {
        this.webClient = WebClient.builder()
            .baseUrl(stackOverFlowApi)
            .build();
    }

    public String getUser(String name) {
        return webClient.get()
            .uri(name)
            .exchange()
            .block()
            .bodyToMono(String.class)
            .block();
    }


}
