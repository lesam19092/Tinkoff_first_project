package edu.java.configuration.access;

import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.impl.ChatRepositoryImpl;
import edu.java.service.jdbc.impl.LinkRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean    //интерфейс                  //сама реализация
    public LinkRepository linkRepository(LinkRepositoryImpl linkRepositoryImpl) {
        //промежуточная штука
        return new JdbcLinkService(linkRepositoryImpl);
    }

    @Bean
    public ChatRepository chatRepository(
        ChatRepositoryImpl chatRepositoryImpl
    ) {
        return new JdbcChatService(chatRepositoryImpl);
    }
}
