package edu.java.component;

import edu.java.model.dto.Link;
import edu.java.model.request.LinkUpdateRequest;
import edu.java.repository.LinkRepository;
import java.net.URISyntaxException;
import java.util.List;
import edu.java.service.sender.SenderService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateScheduler {

    @Value("${app.linkDelay}")
    private int linkDelay;

    private final LinkRepository linkRepository;
    @Autowired
    private LinkUpdater linkUpdater;

    @Autowired
    private SenderService senderService;

    public LinkUpdateScheduler(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    private final Logger logger = Logger.getLogger(LinkUpdateScheduler.class.getName());

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() throws URISyntaxException {
        logger.info("I'm updating!");
         updateOldLinks(linkDelay);
    }

    private void updateOldLinks(int linkDelay) throws URISyntaxException {
        for (Link link : linkRepository.getOldLinks(linkDelay)) {
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, link.getUrl(), "Обновление данных", List.of(link.getChatId()));
            senderService.updateLink(linkUpdateRequest);

            senderService.updateLink(linkUpdateRequest);

            if (link.getUrl().getHost().equals("github.com")) {
                linkUpdater.updateLinkForGithub(link);
            } else if (link.getUrl().getHost().equals("stackoverflow.com")) {
                linkUpdater.updateLinkForStackOverFlow(link);
            }

        }
    }
}
