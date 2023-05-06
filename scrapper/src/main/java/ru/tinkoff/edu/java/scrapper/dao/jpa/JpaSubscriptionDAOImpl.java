package ru.tinkoff.edu.java.scrapper.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDAO;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;
import ru.tinkoff.edu.java.scrapper.entity.jpa.ChatEntity;
import ru.tinkoff.edu.java.scrapper.entity.jpa.SubscriptionEntity;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jpa")
@Repository
public class JpaSubscriptionDAOImpl implements SubscriptionDAO {

    private final JpaSubscriptionRepository subscriptionRepository;
    private final JpaChatRepository chatRepository;

    @Transactional
    @Override
    public int add(Long chatId, Long linkId) {
        if (subscriptionRepository.findByChatIdAndLinkId(chatId, linkId) != null) {
            return 0;
        }
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(chatId, linkId);
        ChatEntity chatEntity = chatRepository.getById(chatId);
        chatEntity.getSubscription().add(subscriptionEntity);
        chatRepository.save(chatEntity);
        return 1;

    }

    @Transactional
    @Override
    public int remove(Long chatId, Long linkId) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByChatIdAndLinkId(chatId, linkId);
        if (subscriptionEntity == null) {
            return 0;
        }
        ChatEntity chatEntity = chatRepository.getById(chatId);
        chatEntity.getSubscription().remove(subscriptionEntity);
        chatRepository.save(chatEntity);
        return 1;
    }

    @Transactional
    @Override
    public Subscription find(Long chatId, Long linkId) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByChatIdAndLinkId(chatId, linkId);
        return convertFromEntity(subscriptionEntity);
    }


    private Subscription convertFromEntity(SubscriptionEntity entity) {
        Subscription subscription = new Subscription();
        subscription.setChatId(entity.getChatId());
        subscription.setLinkId(entity.getLinkId());
        return subscription;
    }

}