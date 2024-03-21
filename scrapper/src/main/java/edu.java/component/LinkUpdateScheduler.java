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
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateScheduler {

    private final JdbcLinkService jdbcLinkService;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private StackOverFlowClient stackOverFlowClient;

    private final BotClient botClient = new BotClient(WebClient.builder().build());

    public LinkUpdateScheduler(JdbcLinkService jdbcLinkService) {
        this.jdbcLinkService = jdbcLinkService;
    }

    private final Logger logger = Logger.getLogger(LinkUpdateScheduler.class.getName());

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() throws InterruptedException, URISyntaxException {
        Thread.sleep(Integer.parseInt(System.getenv("sleep"))); //TODO remove
        logger.info("I'm updating!");
        //  updateOldLinks();
    }

    private void updateOldLinks() throws URISyntaxException {
        for (Link link : jdbcLinkService.getOldLinks()) {
            if (link.getUrl().getHost().equals("github.com")) {
                updateLinkForGithub(link);
            } else if (link.getUrl().getHost().equals("stackoverflow.com")) {
                updateLinkForStackOverFlow(link);
            }
        }
    }

    private void updateLinkForGithub(Link link) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        int idName = Integer.parseInt(System.getenv("idName"));
        int idOfReposName = Integer.parseInt(System.getenv("idReposName"));
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        GitHubRepository rep =
            gitHubClient.getRepositoryInfo(fragments.get(idName), fragments.get(idOfReposName)).block();
        Timestamp lastPush = rep.getLastPush();
        if (lastPush.after(link.getLastCheckTime())) {
            jdbcLinkService.updateLinkLastCheckTimeById(link.getId(), now);
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
        }
    }

    private void updateLinkForStackOverFlow(Link link) throws URISyntaxException {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        int idOfQuestion = Integer.parseInt(System.getenv("idQuestionName"));
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
