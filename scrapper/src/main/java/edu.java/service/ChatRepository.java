package edu.java.service;

import edu.java.model.dto.Chat;
import java.util.List;

public interface ChatRepository {
    List<Chat> getChats();

    void add(Chat chat);

    void remove(Long id);
}
