package ru.tinkoff.edu.java.scrapper.service;

import java.net.URL;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.common.exception.SubscriptionNotFountException;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDAO;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;

@Slf4j
@AllArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final LinkDAO linkDAO;
    private final SubscriptionDAO subscriptionDAO;

    @Transactional
    @Override
    public Link add(long chatId, URL url) {
        Link link = linkDAO.findByUrl(url);
        long linkId;
        if (link == null) {
            link = linkDAO.add(url);
            subscriptionDAO.add(chatId, link.getId());
        } else {
            linkId = link.getId();
            Subscription subscription = subscriptionDAO.find(chatId, linkId);
            if (subscription == null) {
                subscriptionDAO.add(chatId, linkId);
            }
        }
        log.info("{} add subscription on {}", chatId, url);
        return link;
    }

    @Transactional
    @Override
    public Link remove(long chatId, URL url) throws SubscriptionNotFountException {
        Link link = linkDAO.findByUrl(url);
        if (link == null) {
            log.error("{} try untrack from {}, but link not added", chatId, url);
            throw new SubscriptionNotFountException();
        }

        long count = subscriptionDAO.remove(chatId, link.getId());
        if (count == 0) {
            log.error("{} try untrack from {}, but was not subscribed", chatId, url);
            throw new SubscriptionNotFountException();
        }
        log.info("{} untrack from {}", chatId, url);
        return link;
    }

    @Transactional
    @Override
    public List<Link> listAll(long chatId) {
        log.info("{} got list links ", chatId);
        return linkDAO.findAllByChat(chatId);
    }

}
