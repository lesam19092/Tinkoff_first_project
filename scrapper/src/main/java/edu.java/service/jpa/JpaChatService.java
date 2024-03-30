package edu.java.service.jpa;

import edu.java.model.dto.Chat;
import edu.java.repository.ChatRepository;
import edu.java.service.jpa.impl.JpaChatRepositoryImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatRepository {
    private final JpaChatRepositoryImpl chatRepository;

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public void add(Chat chat) {

    }

    @Override
    public void remove(Long id) {

    }
}
