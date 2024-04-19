package edu.java.configuration.access;

import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jooq.JooqLinkService;
import edu.java.service.jooq.impl.JooqChatRepositoryImpl;
import edu.java.service.jooq.impl.JooqLinkRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean    //интерфейс                  //сама реализация
    public LinkRepository linkRepository(JooqLinkRepositoryImpl linkRepositoryImpl) {
        //промежуточная штука
        return new JooqLinkService(linkRepositoryImpl);
    }

    @Bean
    public ChatRepository chatRepository(
        JooqChatRepositoryImpl chatRepositoryImpl
    ) {
        return new JdbcChatService(chatRepositoryImpl);
    }
}
