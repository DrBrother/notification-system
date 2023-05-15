package ru.tinkoff.edu.java.scrapper.dao.jooq;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.ChatRecord;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.CHAT;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.SUBSCRIPTION;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jooq")
@Repository
public class JooqChatDAOImpl implements ChatDAO {

    private final DSLContext dslContext;

    @Transactional
    @Override
    public void add(long chatId) {
        dslContext.insertInto(CHAT, CHAT.ID)
                .values(chatId)
                .onConflictDoNothing()
                .execute();
    }

    @Transactional
    @Override
    public void remove(long chatId) {
        dslContext.delete(CHAT)
                .where(CHAT.ID.eq(chatId))
                .execute();
    }

    @Transactional
    @Override
    public List<ru.tinkoff.edu.java.scrapper.entity.Chat> findAllByLink(long linkId) {
        return dslContext.select(CHAT)
                .from(CHAT)
                .join(SUBSCRIPTION).on(CHAT.ID.eq(SUBSCRIPTION.CHAT_ID))
                .where(SUBSCRIPTION.LINK_ID.eq(linkId))
                .fetch()
                .map(this::convertFromRecord1);
    }

    private ru.tinkoff.edu.java.scrapper.entity.Chat convertFromRecord1(Record1 record1) {
        ChatRecord chatRecord = (ChatRecord) record1.getValue(0);
        return new ru.tinkoff.edu.java.scrapper.entity.Chat(chatRecord.getValue(CHAT.ID));
    }

}
