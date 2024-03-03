package edu.java.bot.model;

import edu.java.bot.model.Request.AddLinkRequest;
import edu.java.bot.model.Request.RemoveLinkRequest;
import edu.java.bot.model.Response.LinkResponse;
import edu.java.bot.model.Response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private final String url = "http://localhost:1010";

    private final String accept = "Accept";
    private final String aplicationJson = "application/json";

    private final String tgChatId = "Tg-Chat-Id";
    private final String tgChatIdUri = "/tg-chat/{id}";

    private final String links = "/links";

    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ListLinksResponse getLinksById(Long chatId) {
        return webClient
            .get()
            .uri(url + links)
            .header(accept, aplicationJson)
            .header(tgChatId, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public LinkResponse addLinkById(Long chatId, AddLinkRequest addLinkRequest) {
        return webClient
            .post()
            .uri(url + links)
            .body(Mono.just(addLinkRequest), AddLinkRequest.class)
            .header(accept, aplicationJson)
            .header(tgChatId, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse deleteLinkById(Long chatId, RemoveLinkRequest removeLinkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(url + links)
            .body(Mono.just(removeLinkRequest), RemoveLinkRequest.class)
            .header(accept, aplicationJson)
            .header(tgChatId, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public String addChatById(@PathVariable String chatId) {
        return webClient
            .post()
            .uri(url + tgChatIdUri, chatId)
            .header(accept, aplicationJson)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    public String deleteChatById(@PathVariable String chatId) {
        return webClient
            .delete()
            .uri(url + tgChatIdUri, chatId)
            .header(accept, aplicationJson)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
