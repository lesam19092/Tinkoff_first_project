package edu.java.service.jooq.impl;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.jooq.Tables.CHAT;


@Repository
public class JooqChatRepositoryImpl implements ChatRepository {
    private final DSLContext dslContext;

    public JooqChatRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Chat> findAll() {
        return dslContext.selectFrom(CHAT).fetchInto(Chat.class);
    }

    public void add(Chat entity) {
        dslContext
            .insertInto(CHAT)
            .set(CHAT.ID, entity.getId())
            .set(CHAT.CHAT_ID, entity.getChatId())
            .returning(CHAT)
            .fetchOne();
    }

    public void remove(Long id) {
        dslContext.deleteFrom(CHAT)
            .where(CHAT.ID.equal(id))
            .execute();
    }
}

