package edu.java.configuration;

import edu.java.GitHubService;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientConfiguration{

    public GitHubClient gitHubClientBuilder;
    public StackOverFlowClient stackOverFlowClient;

    public GitHubService gitHubService(){
        return null;
    }
    public StackOverFlowService stackOverFlowService(){
        return null;
    }
    @Bean
    public WebClient.Builder gitHubClientBuilder() {
        return WebClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN");
    }
}
