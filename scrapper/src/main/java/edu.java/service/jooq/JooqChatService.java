package edu.java.service.jooq;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqChatService implements ChatRepository {
    private final ChatRepository chatRepository;

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public void add(Chat chat) {
        chatRepository.add(chat);
    }

    @Override
    public void remove(Long id) {
        chatRepository.remove(id);
    }
}
