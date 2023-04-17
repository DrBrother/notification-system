package ru.tinkoff.edu.java.scrapper.entity.jdbc.rowMapper;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.entity.Chat;

import java.sql.ResultSet;

public class ChatRowMapper implements RowMapper<Chat> {

    @SneakyThrows
    @Override
    public Chat mapRow(ResultSet rs, int rowNum) {
        Chat chat = new Chat();
        chat.setId(rs.getLong("id"));
        return chat;
    }

}