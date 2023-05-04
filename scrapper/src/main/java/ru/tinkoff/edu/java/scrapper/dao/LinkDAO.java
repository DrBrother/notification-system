package ru.tinkoff.edu.java.scrapper.dao;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.entity.Link;

public interface LinkDAO {

    Link add(URL link);

    void update(Link link);

    Link findByUrl(URL link);

    List<Link> findAllByChat(Long chatId);

    List<Link> findOlderThan(OffsetDateTime checkTime);

}
