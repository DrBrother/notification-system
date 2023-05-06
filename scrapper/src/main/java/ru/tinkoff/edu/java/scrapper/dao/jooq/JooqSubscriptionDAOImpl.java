package ru.tinkoff.edu.java.scrapper.dao.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDAO;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.SubscriptionRecord;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.SUBSCRIPTION;

@AllArgsConstructor
//@Repository
public class JooqSubscriptionDAOImpl implements SubscriptionDAO {

    private final DSLContext dslContext;

    @Transactional
    @Override
    public int add(Long chatId, Long linkId) {
        return dslContext.insertInto(SUBSCRIPTION, SUBSCRIPTION.CHAT_ID, SUBSCRIPTION.LINK_ID)
                .values(chatId, linkId)
                .onConflictDoNothing()
                .execute();
    }

    @Transactional
    @Override
    public int remove(Long chatId, Long linkId) {
        return dslContext.delete(SUBSCRIPTION)
                .where(SUBSCRIPTION.CHAT_ID.eq(chatId), SUBSCRIPTION.LINK_ID.eq(linkId))
                .execute();
    }

    @Transactional
    @Override
    public ru.tinkoff.edu.java.scrapper.entity.Subscription find(Long chatId, Long linkId) {
        return dslContext.select(SUBSCRIPTION)
                .from(SUBSCRIPTION)
                .where(SUBSCRIPTION.CHAT_ID.eq(chatId), SUBSCRIPTION.LINK_ID.eq(linkId))
                .fetch()
                .map(this::convert).stream().findFirst().orElse(null);
    }

    private ru.tinkoff.edu.java.scrapper.entity.Subscription convert(Record1 record1) {
        SubscriptionRecord subscriptionRecord = (SubscriptionRecord) record1.getValue(0);
        return new ru.tinkoff.edu.java.scrapper.entity.Subscription(subscriptionRecord.getValue(SUBSCRIPTION.CHAT_ID),
                subscriptionRecord.getValue(SUBSCRIPTION.LINK_ID));
    }

}