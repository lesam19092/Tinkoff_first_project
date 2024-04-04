package edu.java.service.kafka;

import edu.java.model.request.LinkUpdateRequest;
import edu.java.service.sender.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(value = "app.use-queue", havingValue = "true", matchIfMissing = true)
public class ScrapperQueueProducer implements SenderService {
    private final NewTopic topic;

    private final KafkaTemplate<String, String> template;

    public ScrapperQueueProducer(NewTopic topic, KafkaTemplate<String, String> template) {
        this.topic = topic;
        this.template = template;
    }
    //TODO исправить

    @Override
    public void updateLink(LinkUpdateRequest linkUpdateRequest) {
        try {
       //     template.send(topic.name(), "scrapper queue producer");
        } catch (Exception ex) {
            log.error("Error occurred during sending to Kafka", ex);
        }
    }
}
