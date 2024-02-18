package edu.java.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class GitHubClient {


    /*
    WebClient webClient = WebClient.builder().baseUrl("https://api.github.com/").build();
WebClientAdapter adapter = WebClientAdapter.create(webClient);
HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

RepositoryService service = factory.createClient(RepositoryService.class);
     */

    private final String gitApi = "https://api.github.com/";
    private final WebClient webClient;

    public GitHubClient() {
        this.webClient = WebClient.builder()
            .baseUrl(gitApi)
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
