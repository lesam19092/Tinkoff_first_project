package edu.java.component;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.stackoverflow.StackOverFlowClient;
import edu.java.stackoverflow.StackOverFlowQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Updater implements LinkUpdater {

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private StackOverFlowClient stackOverFlowClient;

    private final BotClient botClient = new BotClient(WebClient.builder().build());
    private final JdbcLinkService jdbcLinkService;

    private final int idName = Integer.parseInt(System.getenv("idName"));

    private final int idOfReposName = Integer.parseInt(System.getenv("idReposName"));

    private final int idOfQuestion = Integer.parseInt(System.getenv("idQuestionName"));

    public Updater(JdbcLinkService jdbcLinkService) {
        this.jdbcLinkService = jdbcLinkService;
    }

    public void updateLinkForGithub(Link link) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        GitHubRepository rep =
            gitHubClient.getRepositoryInfo(fragments.get(idName), fragments.get(idOfReposName)).block();
        Timestamp lastPush = rep.getLastPush();
        if (lastPush.after(link.getLastCheckTime())) {
            jdbcLinkService.updateLinkLastCheckTimeById(link.getId(), now);
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
        }

    }

    @Override
    public void updateLinkForStackOverFlow(Link link) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        StackOverFlowQuestion
            question =
            stackOverFlowClient.fetchQuestion(Long.parseLong(fragments.get(idOfQuestion))).block().getItems()
                .getFirst();
        Timestamp lastActivity = question.getLastActivityAsTimestamp();
        if (lastActivity.after(link.getLastCheckTime())) {
            jdbcLinkService.updateLinkLastCheckTimeById(link.getId(), now);
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
        }

    }
}
