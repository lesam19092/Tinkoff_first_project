package edu.java.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfiguration {



    @Value("${app.kafka.topics}")
    private String topicName;

    @Value("${app.kafka.partitions-num}")
    private int partitions;


    @Value("${app.kafka.replicas-num}")
    private int replicas;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
            .partitions(partitions)
            .replicas(replicas)
            .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("topic1", "test");//TODO ПРИДУМАТЬ ЧТО-НИБУДЬ С ЭТИМ
        };
    }

}
