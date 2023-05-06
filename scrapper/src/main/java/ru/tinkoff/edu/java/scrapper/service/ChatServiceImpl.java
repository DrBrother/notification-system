package ru.tinkoff.edu.java.scrapper.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;

@Slf4j
@AllArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatDAO chatDAO;

    @Transactional
    @Override
    public void register(long chatId) {
        chatDAO.add(chatId);
        log.info("Added chat with id {}", chatId);
    }

    @Transactional
    @Override
    public void unregister(long chatId) {
        chatDAO.remove(chatId);
        log.info("Removed chat with id {}", chatId);
    }

}