package edu.java.component;

import edu.java.client.BotClient;
import edu.java.exception.ClientException;
import edu.java.exception.ServerException;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.repository.LinkRepository;
import edu.java.stackoverflow.StackOverFlowClient;
import edu.java.stackoverflow.StackOverFlowQuestion;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Component
public class Updater implements LinkUpdater {
    private final Logger logger = Logger.getLogger(Updater.class.getName());

    private final LinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverFlowClient stackOverFlowClient;
    private final BotClient botClient;

    public Updater(
        LinkRepository linkRepository,
        GitHubClient gitHubClient,
        StackOverFlowClient stackOverFlowClient,
        BotClient botClient
    ) {
        this.linkRepository = linkRepository;
        this.gitHubClient = gitHubClient;
        this.stackOverFlowClient = stackOverFlowClient;
        this.botClient = botClient;
    }

    @Override
    public void updateLinkForGithub(Link link) throws URISyntaxException {

        try {

            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            List<String> fragments = List.of(link.getUrl().toString().split("/"));
            GitHubRepository rep = gitHubClient
                .getRepositoryInfo(fragments.get(Integer.parseInt("3")), fragments.get(Integer.parseInt("4")))
                .block();
            Timestamp lastPush = rep.getLastPush();
            if (lastPush.after(link.getLastCheckTime())) {
                linkRepository.updateLinkLastCheckTimeById(link.getId(), now);
                botClient.updateLink(link.getUrl(), List.of(link.getChatId()), "обновление данных");
            }
        } catch (ServerException | ClientException | WebClientRequestException ex) {
            logger.error(ex.getMessage());
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

        try {

            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            Timestamp lastActivity = question.getLastActivityAsTimestamp();
            if (lastActivity.after(link.getLastCheckTime())) {
                List<DescriptionType> lisOfDescriptions = new ArrayList<>();
                lisOfDescriptions.add(DescriptionType.UPDATING_DATA);
                linkRepository.updateLinkLastCheckTimeById(link.getId(), now);
                if (question.getAnswerCount() > linkRepository.getLinkPropertiesById(link.getId()).getCountOfAnswer()) {
                    lisOfDescriptions.add(DescriptionType.NEW_COMMENT);
                    linkRepository.updateCountOfAnswersById(link.getId(), question.getAnswerCount());
                }
                if (question.getCommentCount()
                    > linkRepository.getLinkPropertiesById(link.getId()).getCountOfComments()) {
                    lisOfDescriptions.add(DescriptionType.NEW_ANSWER);
                    linkRepository.updateCountOfCommentsById(link.getId(), question.getCommentCount());
                }
                String description = DescriptionType.getDescription(lisOfDescriptions);
                botClient.updateLink(link.getUrl(), List.of(link.getChatId()), description);

            }
        } catch (ClientException | ServerException e) {
            logger.error(e.getMessage());
        }
    }
}
