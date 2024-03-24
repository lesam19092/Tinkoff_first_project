package edu.java.repository;

import edu.java.model.dto.Chat;
import java.util.List;

public interface ChatRepository {
    List<Chat> findAll();

    void add(Chat chat);

    void remove(Long id);
}
