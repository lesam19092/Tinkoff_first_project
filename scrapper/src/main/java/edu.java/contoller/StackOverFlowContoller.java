package edu.java.contoller;

import edu.java.model.GitHub.ModelGitHubRepository;
import edu.java.model.StackOverFlow.ModelStackOverFlowQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@RestController
public class StackOverFlowContoller {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/question/{id}")
    public ModelStackOverFlowQuestion getQuestion(
        @PathVariable("id") Long id
    ) {
        //  https://api.github.com/repos/stukenvitalii/bot_temp/
        https://api.stackexchange.com/2.3/questions/4070716?order=desc&sort=activity&site=stackoverflow
        return webClientBuilder.build()
            .get()
            .uri("https://api.stackexchange.com/2.3/questions/"+id+"?order=desc&sort=activity&site=stackoverflow") //TODO РАЗБИТЬ КРАСИВО
            .retrieve()
            .bodyToMono(ModelStackOverFlowQuestion.class)
            .block();
    }

    /*@GetMapping("/question/{id}")
    public ResponseEntity<ModelStackOverFlowQuestion> getQuestion(
        @PathVariable("id") Long id
    ) {
        // https://api.github.com/repos/stukenvitalii/bot_temp/
        // https://api.stackexchange.com/2.3/questions/4070716?order=desc&sort=activity&site=stackoverflow

        try {
            ModelStackOverFlowQuestion question = webClientBuilder.build()
                .get()
                .uri("https://api.stackexchange.com/2.3/questions/" + id + "?order=desc&sort=activity&site=stackoverflow")
                .retrieve()
                .bodyToMono(ModelStackOverFlowQuestion.class)
                .block();

            return ResponseEntity.ok().body(question);
        } catch (WebClientException e) {
            // Обработка ошибок HTTP (404, 500 etc.)
            return null;
        } catch (Exception e) {
            // Обработка других ошибок (десериализация, JSON)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}
