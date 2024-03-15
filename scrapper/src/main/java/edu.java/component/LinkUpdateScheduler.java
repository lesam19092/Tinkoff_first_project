package edu.java.component;

import edu.java.client.BotClient;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.model.dto.LinkSof;
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
       // Thread.sleep(Integer.parseInt(System.getenv("sleep"))); //TODO remove
        logger.info("I'm updating!");
        updateOldLinks();
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
        int idName = Integer.parseInt(System.getenv("idName"));
        int idOfReposName = Integer.parseInt(System.getenv("idReposName"));
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        GitHubRepository rep =
            gitHubClient.getRepositoryInfo(fragments.get(idName), fragments.get(idOfReposName)).block();
        Timestamp lastPush = rep.getLastPush();
        if (lastPush.after(link.getLastCheckTime())) {
            link.setLastCheckTime(Timestamp.valueOf(LocalDateTime.now())); //TODO update to DB
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
        }
    }

    private void updateLinkForStackOverFlow(Link link) {
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        System.out.println(fragments.toString());
        //int idOfQuestion = Integer.parseInt(System.getenv("idOfQuestion"));
        StackOverFlowQuestion
            question =
            stackOverFlowClient.fetchQuestion(Long.parseLong(fragments.get(4))).getItems()
                .getFirst();
        Timestamp lastActivity = question.getLastActivityAsTimestamp();

            LinkSof linkSof = jdbcLinkService.getLinkPropertiesById(link.getId());

        System.out.println(linkSof.getLinkId() + " " + linkSof.getCounfOfAnswer());

            System.out.println(linkSof.getCounfOfAnswer());
            if (question.getCommentCount() > linkSof.getCountOfComments()){
                System.out.println("dfsfdsfdsfdsf121212121212");
            }
            if(question.getAnswerCount() > linkSof.getCounfOfAnswer()){
                System.out.println("kek");
            }
            //botClient.updateLink(link.getUrl(), List.of(link.getChatId()));

            link.setLastCheckTime(Timestamp.valueOf(LocalDateTime.now())); //TODO update to DB

    }

}
