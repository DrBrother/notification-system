package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.common.exception.SubscriptionNotFountException;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URL;
import java.util.List;

public interface SubscriptionService {

    Link add(long tgChatId, URL url);

    Link remove(long tgChatId, URL url) throws SubscriptionNotFountException;

    List<Link> listAll(long tgChatId);

}