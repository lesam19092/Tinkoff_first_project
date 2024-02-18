package edu.java;

import org.springframework.web.reactive.function.client.WebClient;

public class GitHubService {
    private final WebClient.Builder webClientBuilder;

    public GitHubService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Commit> getCommits(String repository) {
        WebClient webClient = webClientBuilder.build();
        return webClient.get()
            .uri("/repos/{owner}/{repo}/commits", "octocat", repository)
            .retrieve()
            .bodyToFlux(Commit.class)
            .collectList()
            .block();
    }
}
