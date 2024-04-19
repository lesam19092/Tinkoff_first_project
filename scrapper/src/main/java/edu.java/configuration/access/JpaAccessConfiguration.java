package edu.java.configuration.access;

import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.impl.JpaChatRepositoryImpl;
import edu.java.service.jpa.impl.JpaLinkRepositoryImpl;
import edu.java.service.jpa.impl.JpaSofRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean    //интерфейс                  //сама реализация
    public LinkRepository linkRepository(
        JpaLinkRepositoryImpl linkRepositoryImpl,
        JpaSofRepositoryImpl jpaSofRepository
    ) {
        //промежуточная штука
        return new JpaLinkService(linkRepositoryImpl, jpaSofRepository);
    }

    @Bean
    public ChatRepository chatRepository(
        JpaChatRepositoryImpl chatRepositoryImpl
    ) {
        return new JpaChatService(chatRepositoryImpl);
    }
}
