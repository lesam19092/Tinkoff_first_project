package edu.java.service.jdbc.impl;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {
    private final JdbcClient jdbcClient;
    private final Logger logger = Logger.getLogger(ChatRepositoryImpl.class.getName());

    @Transactional
    public void add(Chat chatEntity) {
        String sql =
            "insert into chat(chat_id) values(:chatId)";
        jdbcClient.sql(sql)
            .param("chatId", chatEntity.getChatId())
            .update();
    }

    @Transactional
    public void remove(Long chatId) {
        String sql = "delete from chat where chat_id = ?";
        int count = jdbcClient.sql(sql).param(1, chatId).update();
        if (count == 0) {
            logger.info("troubles with removing");
            throw new RuntimeException("chat not found");
        }
        logger.info("entity was deleted");
    }

    public List<Chat> findAll() {
        String sql = "select * from chat";
        return jdbcClient.sql(sql).query(Chat.class).list();
    }
}
