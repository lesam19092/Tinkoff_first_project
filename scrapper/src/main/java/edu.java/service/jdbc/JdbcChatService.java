package edu.java.service.jdbc;

import edu.java.model.Chat;
import edu.java.service.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcChatService {

    private final ChatRepository chatRepository;

    public List<Chat> getChats() {
        return chatRepository.getChats();
    }

    public void addChat(Chat chat) {
        chatRepository.add(chat);
    }

    public void removeChat(Long id) {
        chatRepository.remove(id);
    }
}
