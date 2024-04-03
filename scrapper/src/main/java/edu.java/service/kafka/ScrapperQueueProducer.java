package edu.java.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScrapperQueueProducer {
    private final NewTopic topic;

    private final KafkaTemplate<String, String> template;

    public ScrapperQueueProducer(NewTopic topic, KafkaTemplate<String, String> template) {
        this.topic = topic;
        this.template = template;
    }

    public void send() {
        try {
            template.send(topic.name(), "scrapper queue producer");
        } catch (Exception ex) {
            log.error("Error occurred during sending to Kafka", ex);
        }
    }
}
