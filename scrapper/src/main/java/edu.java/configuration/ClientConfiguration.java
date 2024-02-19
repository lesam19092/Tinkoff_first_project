package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientConfiguration{

  //  public GitHubClient gitHubClientBuilder;
  //  public StackOverFlowClient stackOverFlowClient;
    private final String gitApi = "https://api.github.com/";

    private final String stackOverFlowApi = "https://api.stackexchange.com/2.3" ;


    @Bean
    public WebClient.Builder getStackOverFlowWebClientBuilder() {
        return WebClient.builder()
            .baseUrl(stackOverFlowApi);
    }

    @Bean
    public WebClient.Builder getGitHubWebClientBuilder(){
        return WebClient.builder().baseUrl(gitApi);
    }
}
