package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Chat;

import java.util.List;

public interface ChatDAO {

    int add(long chatId);

    int remove(long chatId);

    List<Chat> findAllByLink(long linkId);

}