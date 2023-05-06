package ru.tinkoff.edu.java.scrapper.dao;

import ru.tinkoff.edu.java.scrapper.entity.Subscription;

public interface SubscriptionDAO {

    int add(Long chatId, Long linkId);

    int remove(Long chatId, Long linkId);

    Subscription find(Long chatId, Long linkId);

}