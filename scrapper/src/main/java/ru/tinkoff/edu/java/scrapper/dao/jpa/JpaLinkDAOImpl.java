package ru.tinkoff.edu.java.scrapper.dao.jpa;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.jpa.LinkEntity;
import ru.tinkoff.edu.java.scrapper.entity.jpa.SubscriptionEntity;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jpa")
@Repository
public class JpaLinkDAOImpl implements LinkDAO {

    private final JpaLinkRepository jpaLinkRepository;
    private final JpaSubscriptionRepository jpaSubscriptionRepository;

    @Transactional
    @Override
    public Link add(URL link) {
        OffsetDateTime now = OffsetDateTime.now();
        return convertFromEntity(jpaLinkRepository.save(new LinkEntity(link.toString(), now, now)));
    }

    @Transactional
    @Override
    public void update(Link link) {
        Set<SubscriptionEntity> subscription = jpaSubscriptionRepository.findAllByLinkId(link.getId());
        jpaLinkRepository.save(convertToEntity(link, subscription));
    }

    @Transactional
    @Override
    public Link findByUrl(URL link) {
        return convertFromEntity(jpaLinkRepository.findByUrl(link.toString()));
    }

    @Transactional
    @Override
    public List<Link> findAllByChat(Long chatId) {
        return jpaLinkRepository.findAllByChat(chatId).stream().map(this::convertFromEntity).toList();
    }

    @Transactional
    @Override
    public List<Link> findOlderThan(OffsetDateTime checkTime) {
        return jpaLinkRepository.findAllByCheckTimeBefore(checkTime).stream().map(this::convertFromEntity).toList();
    }

    private LinkEntity convertToEntity(Link link, Set<SubscriptionEntity> subscriptionEntitySet) {
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setId(link.getId());
        linkEntity.setUrl(link.getUrl().toString());
        linkEntity.setUpdateTime(link.getUpdateTime());
        linkEntity.setCheckTime(OffsetDateTime.now());
        linkEntity.setSubscription(subscriptionEntitySet);
        return linkEntity;
    }

    @SneakyThrows
    private Link convertFromEntity(LinkEntity linkEntity) {
        return new Link(linkEntity.getId(), new URL(linkEntity.getUrl()), linkEntity.getCheckTime(), linkEntity.getUpdateTime());
    }

}