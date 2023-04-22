package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.entity.Chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jdbc")
@Repository
public class JdbcChatDAOImpl implements ChatDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_LINK = "SELECT * FROM chat JOIN subscription s on chat.id = s.chat_id WHERE link_id = (?)";
    private static final String INSERT = "INSERT INTO chat VALUES (?) ON CONFLICT DO NOTHING";
    private static final String DELETE = "DELETE FROM chat WHERE id = (?)";

    @Transactional
    @Override
    public void add(long chatId) {
        jdbcTemplate.update(INSERT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
            }
        });
    }

    @Transactional
    @Override
    public void remove(long chatId) {
        jdbcTemplate.update(DELETE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
            }
        });
    }

    @Transactional
    @Override
    public List<Chat> findAllByLink(long linkId) {
        return jdbcTemplate.query(SELECT_BY_LINK, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, linkId);
            }
        }, this::mapRow);
    }

    @SneakyThrows
    public Chat mapRow(ResultSet rs, int rowNum) {
        Chat chat = new Chat();
        chat.setId(rs.getLong("id"));
        return chat;
    }

}