package edu.java.component;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.repository.jdbc.JdbcLinkService;
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
    private final BotClient botClient = new BotClient(WebClient.builder().build());

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
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()), "обновление данных");
        }
    }

    @Override
    public void updateLinkForStackOverFlow(Link link) throws URISyntaxException {
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        StackOverFlowQuestion
            question =
            stackOverFlowClient
                .fetchQuestion(Long.parseLong(fragments.get(Integer.parseInt("4"))))
                .getItems()
                .getFirst();
        getUpdatesFromSof(link, question);
    }

    private void getUpdatesFromSof(Link link, StackOverFlowQuestion question) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lastActivity = question.getLastActivityAsTimestamp();
        if (lastActivity.after(link.getLastCheckTime())) {
            String description = "обновление данных : ";
            jdbcLinkService.updateLinkLastCheckTimeById(link.getId(), now);
            if (question.getAnswerCount() > jdbcLinkService.getLinkPropertiesById(link.getId()).getCountOfAnswer()) {
                description += "\n"
                    + "появился новый ответ";
                jdbcLinkService.updateCountOfAnswersById(link.getId(), question.getAnswerCount());
            }

            if (question.getCommentCount() > jdbcLinkService.getLinkPropertiesById(link.getId()).getCountOfComments()) {
                description += "\n"
                    + "появился новый комментарий";
                jdbcLinkService.updateCountOfCommentsById(link.getId(), question.getCommentCount());
            }
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()), description);

        }
    }
}
