package edu.java.component;

import edu.java.exception.ClientException;
import edu.java.exception.ServerException;
import edu.java.github.GitHubClient;
import edu.java.github.GitHubRepository;
import edu.java.model.dto.Link;
import edu.java.model.request.LinkUpdateRequest;
import edu.java.repository.LinkRepository;
import edu.java.service.sender.SenderService;
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
    private final SenderService senderService;

    public Updater(
        LinkRepository linkRepository,
        GitHubClient gitHubClient,
        StackOverFlowClient stackOverFlowClient,
        SenderService senderService
    ) {
        this.linkRepository = linkRepository;
        this.gitHubClient = gitHubClient;
        this.stackOverFlowClient = stackOverFlowClient;
        this.senderService = senderService;
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
                LinkUpdateRequest linkUpdateRequest =
                    new LinkUpdateRequest(link.getId(), link.getUrl(), "обновление данных",
                        List.of(link.getChatId())
                    );
                senderService.updateLink(linkUpdateRequest);
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
                LinkUpdateRequest linkUpdateRequest =
                    new LinkUpdateRequest(link.getId(), link.getUrl(), description, List.of(link.getChatId()));
                senderService.updateLink(linkUpdateRequest);

            }
        } catch (ClientException | ServerException e) {
            logger.error(e.getMessage());
        }
    }
}
