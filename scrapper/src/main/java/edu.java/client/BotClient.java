package edu.java.client;

import edu.java.model.request.LinkUpdateRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private final String baseUrl = "http://localhost:8070";

    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String updateLink(URI url, List<Long> tgChatIds) throws URISyntaxException {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, url, "Обновление ссылки", tgChatIds);
        return webClient
            .post()
            .uri(baseUrl + "/updates")
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .header("Accept", "application/json")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
