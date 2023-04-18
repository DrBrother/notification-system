package ru.tinkoff.edu.java.scrapper.dao.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.LinkRecord;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.LINK;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.tables.Subscription.SUBSCRIPTION;

@AllArgsConstructor
//@Repository
public class JooqLinkDAOImpl implements LinkDAO {

    private final DSLContext dslContext;

    @Transactional
    @Override
    public ru.tinkoff.edu.java.scrapper.entity.Link add(URL link) {
        return dslContext.insertInto(LINK, LINK.URL)
                .values(link.toString())
                .onConflictDoNothing()
                .returningResult(LINK.ID, LINK.URL, LINK.CHECKTIME, LINK.UPDATETIME)
                .fetchOne()
                .map(this::convert);
    }

    @Transactional
    @Override
    public int update(ru.tinkoff.edu.java.scrapper.entity.Link link) {
        return dslContext.update(LINK)
                .set(LINK.UPDATETIME, link.getUpdateTime().toLocalDateTime())
                .set(LINK.CHECKTIME, OffsetDateTime.now().toLocalDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();
    }

    @Transactional
    @Override
    public ru.tinkoff.edu.java.scrapper.entity.Link findByUrl(URL link) {
        return dslContext.select(LINK)
                .from(LINK)
                .where(LINK.URL.eq(link.toString()))
                .fetch()
                .map(this::convert).stream().findFirst().orElse(null);
    }

    @Transactional
    @Override
    public List<ru.tinkoff.edu.java.scrapper.entity.Link> findAllByChat(Long chatId) {
        return dslContext.select(LINK)
                .from(LINK)
                .join(SUBSCRIPTION).on(LINK.ID.eq(SUBSCRIPTION.LINK_ID))
                .where(SUBSCRIPTION.CHAT_ID.eq(chatId))
                .fetch()
                .map(this::convert);
    }

    @Transactional
    @Override
    public List<ru.tinkoff.edu.java.scrapper.entity.Link> findOlderThan(OffsetDateTime checkTime) {
        return dslContext.select(LINK)
                .from(LINK)
                .where(LINK.CHECKTIME.lessOrEqual(checkTime.toLocalDateTime()))
                .fetch()
                .map(this::convert);
    }


    private ru.tinkoff.edu.java.scrapper.entity.Link convert(Record1 record1) {
        LinkRecord linkRecord = (LinkRecord) record1.get(0);
        try {
            return new ru.tinkoff.edu.java.scrapper.entity.Link(
                    linkRecord.getValue(LINK.ID), new URL(linkRecord.getValue(LINK.URL)),
                    OffsetDateTime.of(linkRecord.getValue(LINK.CHECKTIME), ZoneOffset.UTC),
                    OffsetDateTime.of(linkRecord.getValue(LINK.UPDATETIME), ZoneOffset.UTC));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private ru.tinkoff.edu.java.scrapper.entity.Link convert(Record record) {
        try {
            return new ru.tinkoff.edu.java.scrapper.entity.Link(
                    record.getValue(LINK.ID), new URL(record.getValue(LINK.URL)),
                    OffsetDateTime.of(record.getValue(LINK.CHECKTIME), ZoneOffset.UTC),
                    OffsetDateTime.of(record.getValue(LINK.UPDATETIME), ZoneOffset.UTC));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}