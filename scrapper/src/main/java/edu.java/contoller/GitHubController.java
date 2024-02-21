package edu.java.contoller;

import edu.java.model.GitHub.ModelGitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@RestController

public class GitHubController {

    @Autowired
    private WebClient.Builder webClientBuilder;

  //  @GetMapping("{name}/{reposName}")
 /*   public ModelGitHubRepository getRepositoryInfo(
        @PathVariable("name") String name,
        @PathVariable("reposName") String reposName
    ) {
        //  https://api.github.com/repos/stukenvitalii/bot_temp/
        return webClientBuilder.build()
            .get()
            .uri("https://api.github.com/repos/" + name + "/" + reposName) //TODO РАЗБИТЬ КРАСИВО
            .retrieve()
            .bodyToMono(ModelGitHubRepository.class)
            .block();
    }*/
    @GetMapping("/repos/{name}/{reposName}")
    public ModelGitHubRepository getRepositoryInfo(
        @PathVariable("name") String name,
        @PathVariable("reposName") String reposName
    ) {
        return webClientBuilder.build()
            .get()
            .uri("https://api.github.com/repos/{name}/{reposName}",name,reposName)
            //.headers(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))//TODO РАЗБИТЬ КРАСИВО
            .retrieve()
            .bodyToMono(ModelGitHubRepository.class)
            .block();
    }

   /* @GetMapping("{name}")
    public List<ModelRepository> getRepositories(@PathVariable("name") String name) {
        //  https://api.github.com/users/stukenvitalii/repos
        List<ModelRepository> list = new ArrayList<>();

         webClientBuilder.build()
            .get()
            .uri("https://api.github.com/users/" + name + "/repos")
            .accept(MediaType.APPLICATION_JSON)//TODO сделать через стримы заполнение в список или посмотреть выдаст ли он его сам
            .retrieve()
            .bodyToMono(ModelRepository[].class)
            .subscribe(repositories -> {
            // Обработайте полученные данные
            list.addAll(Arrays.asList(repositories));
        });

         return list;

    }*/

    // https://api.github.com/users/stukenvitalii/repos






    @GetMapping("{name}")
    public Mono<List<ModelGitHubRepository>> getRepositories(@PathVariable("name") String name) {
        return webClientBuilder.build()
            .get()
            .uri("https://api.github.com/users/" + name + "/repos")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ModelGitHubRepository[].class)
            .map(repositories -> Arrays.asList(repositories));
    }



}
