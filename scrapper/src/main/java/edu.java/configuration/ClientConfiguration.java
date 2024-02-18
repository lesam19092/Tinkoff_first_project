package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientConfiguration{

  //  public GitHubClient gitHubClientBuilder;
  //  public StackOverFlowClient stackOverFlowClient;
    private final String gitApi = "https://api.github.com/";

    private final String stackOverFlowApi = "https://api.stackexchange.com/2.2" ;


    /*@Bean
    public WebClient.Builder gitHubClientBuilder() {
        return WebClient.builder()
            .baseUrl(gitApi )
            .defaultHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN");
    }*/

    @Bean
    public WebClient.Builder getGitWebClientBuilder(){
        return WebClient.builder().baseUrl(gitApi);
    }
}
