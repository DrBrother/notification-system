package ru.tinkoff.edu.java.scrapper.dao.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.entity.jooq.tables.records.ChatRecord;

import java.util.List;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.CHAT;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.SUBSCRIPTION;

@AllArgsConstructor
//@Repository
public class JooqChatDAOImpl implements ChatDAO {

    private final DSLContext dslContext;

    @Transactional
    @Override
    public int add(long chatId) {
        return dslContext.insertInto(CHAT, CHAT.ID)
                .values(chatId)
                .onConflictDoNothing()
                .execute();
    }

    @Transactional
    @Override
    public int remove(long chatId) {
        return dslContext.delete(CHAT)
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
                .map(record -> {
                    ChatRecord chatRecord = (ChatRecord) record.getValue(0);
                    return new ru.tinkoff.edu.java.scrapper.entity.Chat(chatRecord.getValue(CHAT.ID));
                });
    }

}