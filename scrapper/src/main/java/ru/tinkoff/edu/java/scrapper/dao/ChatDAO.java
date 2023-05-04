package ru.tinkoff.edu.java.scrapper.dao;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.entity.Chat;

public interface ChatDAO  {

    void add(long chatId);

    void remove(long chatId);

    List<Chat> findAllByLink(long linkId);

}
