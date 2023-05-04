package ru.tinkoff.edu.java.scrapper.dao.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.entity.jpa.LinkEntity;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {

    LinkEntity findByUrl(String url);

    LinkEntity findByCheckTime(OffsetDateTime checkTime);

    List<LinkEntity> findAllByCheckTimeBefore(OffsetDateTime checkTime);

    @Query("select l from LinkEntity l join SubscriptionEntity s where s.linkId=l.id and s.chatId=:chatId")
    List<LinkEntity> findAllByChat(@Param("chatId") Long chatId);

}
