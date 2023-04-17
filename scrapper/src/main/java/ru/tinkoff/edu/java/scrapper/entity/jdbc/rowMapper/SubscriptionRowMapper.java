package ru.tinkoff.edu.java.scrapper.entity.jdbc.rowMapper;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;

import java.sql.ResultSet;

public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @SneakyThrows
    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) {
        Subscription subscription = new Subscription();
        subscription.setChatId(rs.getLong("chat_id"));
        subscription.setLinkId(rs.getLong("link_id"));
        return subscription;
    }

}