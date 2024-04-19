package edu.java.service.jdbc;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcChatService implements ChatRepository {

    private final ChatRepository chatRepository;

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public void add(Chat chat) {
        chatRepository.add(chat);
    }

    public void remove(Long id) {
        chatRepository.remove(id);
    }
}
