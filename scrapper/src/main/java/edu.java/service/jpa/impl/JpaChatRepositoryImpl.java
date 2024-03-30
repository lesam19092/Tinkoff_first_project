package edu.java.service.jpa.impl;

import edu.java.model.dto.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepositoryImpl extends JpaRepository<Chat, Long> {
    Chat save(Chat chatEntity);

    void removeById(Long chatId);

    List<Chat> findAll();
}
