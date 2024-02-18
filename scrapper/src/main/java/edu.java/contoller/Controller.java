package edu.java.contoller;

import edu.java.model.ModelRepository;
import edu.java.model.ModelRepositoryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Controller

public class Controller {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping()
    public ModelRepository getRepositoryInfo(
        @PathVariable("name") String name,
        @PathVariable("reposName") String reposName
    ) {
        //  https://api.github.com/repos/stukenvitalii/bot_temp/
        return webClientBuilder.build()
            .get()
            .uri("https://api.github.com/repos/" + name + "/" + reposName) //TODO РАЗБИТЬ КРАСИВО
            .retrieve()
            .bodyToMono(ModelRepository.class)
            .block();
    }

    @GetMapping("{name}")
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

    }

}
