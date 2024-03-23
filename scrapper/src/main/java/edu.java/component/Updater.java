package edu.java.component;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.stackoverflow.StackOverFlowClient;
import edu.java.stackoverflow.StackOverFlowQuestion;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Updater implements LinkUpdater {

    private final JdbcLinkService jdbcLinkService;
    private final GitHubClient gitHubClient;
    private final StackOverFlowClient stackOverFlowClient;

    public Updater(
        JdbcLinkService jdbcLinkService,
        GitHubClient gitHubClient,
        StackOverFlowClient stackOverFlowClient
    ) {
        this.jdbcLinkService = jdbcLinkService;
        this.gitHubClient = gitHubClient;
        this.stackOverFlowClient = stackOverFlowClient;
    }

    @Override
    public void updateLinkForGithub(Link link) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        GitHubRepository rep = gitHubClient
            .getRepositoryInfo(fragments.get(Integer.parseInt("3")), fragments.get(Integer.parseInt("4")))
            .block();
        Timestamp lastPush = rep.getLastPush();
        if (lastPush.after(link.getLastCheckTime())) {
            jdbcLinkService.updateLinkLastCheckTimeById(link.getId(), now);
            new BotClient(WebClient.builder().build()).updateLink(link.getUrl(), List.of(link.getChatId()));
        }
    }

    @Override
    public void updateLinkForStackOverFlow(Link link) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        StackOverFlowQuestion
            question =
            stackOverFlowClient
                .fetchQuestion(Long.parseLong(fragments.get(Integer.parseInt("4"))))
                .block()
                .getItems()
                .getFirst();
        Timestamp lastActivity = question.getLastActivityAsTimestamp();
        if (lastActivity.after(link.getLastCheckTime())) {
            jdbcLinkService.updateLinkLastCheckTimeById(link.getId(), now);
            new BotClient(WebClient.builder().build()).updateLink(link.getUrl(), List.of(link.getChatId()));
        }
    }
}
