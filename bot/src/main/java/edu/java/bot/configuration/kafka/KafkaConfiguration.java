package edu.java.bot.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

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

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(String in) {
        System.out.println(in);
    }
    //TODO ПРИДУМАТЬ ЧТО-НИБУДЬ С ЭТИМ

}
