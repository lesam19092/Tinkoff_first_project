package edu.java.component;

import edu.java.model.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import java.net.URISyntaxException;
import org.jboss.logging.Logger;
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

    private final JdbcLinkService jdbcLinkService;

    private LinkUpdater linkUpdater;

    public LinkUpdateScheduler(LinkUpdater linkUpdater, JdbcLinkService jdbcLinkService) {
        this.jdbcLinkService = jdbcLinkService;
        this.linkUpdater = linkUpdater;
    }

    private final Logger logger = Logger.getLogger(LinkUpdateScheduler.class.getName());

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() throws URISyntaxException {
        logger.info("I'm updating!");
        updateOldLinks(linkDelay);
    }

    private void updateOldLinks(int linkDelay) throws URISyntaxException {
        for (Link link : jdbcLinkService.getOldLinks(linkDelay)) {
            if (link.getUrl().getHost().equals("github.com")) {
                linkUpdater.updateLinkForGithub(link);
            } else if (link.getUrl().getHost().equals("stackoverflow.com")) {
                linkUpdater.updateLinkForStackOverFlow(link);
            }
        }
    }

}
