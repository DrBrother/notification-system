package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.jdbc.rowMapper.ChatRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class JdbcChatDAOImpl implements ChatDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_LINK = "SELECT * FROM chat JOIN subscription s on chat.id = s.chat_id WHERE link_id = (?)";
    private static final String INSERT = "INSERT INTO chat VALUES (?) ON CONFLICT DO NOTHING";
    private static final String DELETE = "DELETE FROM chat WHERE id = (?)";

    @Transactional
    @Override
    public int add(long chatId) {
        return jdbcTemplate.update(INSERT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
            }
        });
    }

    @Transactional
    @Override
    public int remove(long chatId) {
        return jdbcTemplate.update(DELETE, new PreparedStatementSetter() {
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
        }, new ChatRowMapper());
    }

}