package edu.java.bot.client;

import dto.request.AddLinkRequest;
import dto.request.RemoveLinkRequest;
import dto.response.ApiErrorResponse;
import dto.response.LinkResponse;
import dto.response.ListLinksResponse;
import edu.java.bot.model.exception.ApiException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClient {
    private final String baseUrlLinks = "http://localhost:1010/links";
    private final String baseUrlTgChat = "http://localhost:1010/tg-chat/{id}";
    private final String accept = "Accept";
    private final String applicationJson = "application/json";
    private final String tgChatId = "Tg-Chat-Id";

    private final WebClient webClient;

    public ScrapperClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ListLinksResponse getLinksById(Long chatId) {
        return webClient
            .get()
            .uri(baseUrlLinks)
            .header(accept, applicationJson)
            .header(tgChatId, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public LinkResponse addLinkById(Long chatId, AddLinkRequest addLinkRequest) throws ApiException {
        return webClient
            .post()
            .uri(baseUrlLinks)
            .body(Mono.just(addLinkRequest), AddLinkRequest.class)
            .header(accept, applicationJson)
            .header(tgChatId, String.valueOf(chatId))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> Mono.error(new ApiException(response, "Some error")))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse deleteLinkById(Long chatId, RemoveLinkRequest removeLinkRequest) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(baseUrlLinks)
            .body(Mono.just(removeLinkRequest), RemoveLinkRequest.class)
            .header(accept, applicationJson)
            .header(tgChatId, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public ApiErrorResponse addChatById(@PathVariable Long chatId) {
        return webClient
            .post()
            .uri(baseUrlTgChat, chatId)
            .header(accept, applicationJson)
            .retrieve()
//            .onStatus(HttpStatus.BAD_REQUEST::equals,
//                response -> Mono.error(new ApiException(response, "Smth wrong")))
            .bodyToMono(ApiErrorResponse.class)
            .onErrorResume(ApiException.class, ex -> ex.response.bodyToMono(ApiErrorResponse.class))
            .block();
    }

    public String deleteChatById(@PathVariable Long chatId) {
        return webClient
            .delete()
            .uri(baseUrlTgChat, chatId)
            .header(accept, applicationJson)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
