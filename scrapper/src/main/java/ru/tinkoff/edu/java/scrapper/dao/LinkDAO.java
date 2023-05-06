package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkDAO {

    Link add(URL link);

    int update(Link link);

    Link findByUrl(URL link);

    List<Link> findAllByChat(Long chatId);

    List<Link> findOlderThan(OffsetDateTime checkTime);

}
