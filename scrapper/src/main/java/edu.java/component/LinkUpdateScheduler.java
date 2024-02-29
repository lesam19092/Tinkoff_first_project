package edu.java.component;

import org.jboss.logging.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateScheduler {
    private final Logger logger = Logger.getLogger(LinkUpdateScheduler.class.getName());

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() {
        logger.info("I'm updating!");
    }
}
