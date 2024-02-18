package edu.java.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class GitHubClient {


    /*
    WebClient webClient = WebClient.builder().baseUrl("https://api.github.com/").build();
WebClientAdapter adapter = WebClientAdapter.create(webClient);
HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

RepositoryService service = factory.createClient(RepositoryService.class);
     */


        private final WebClient webClient;

        private final String gitApi = "https://api.github.com/";

        public GitHubClient(WebClient.Builder webClientBuilder) {
            this.webClient = webClientBuilder.baseUrl(gitApi).build();
        }

        public Details someRestCall(String name) {
            return this.webClient.get().uri("/{name}/details", name).retrieve().bodyToMono(Details.class);
        }

    }
