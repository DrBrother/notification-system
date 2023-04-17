package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDAO;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;
import ru.tinkoff.edu.java.scrapper.entity.jdbc.rowMapper.SubscriptionRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class JdbcSubscriptionDAOImpl implements SubscriptionDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT = "SELECT * FROM subscription WHERE chat_id = (?) AND link_id = (?)";
    private static final String INSERT = "INSERT INTO subscription  VALUES (?,?) ON CONFLICT DO NOTHING";
    private static final String DELETE = "DELETE FROM subscription WHERE chat_id = (?) AND link_id = (?)";

    @Transactional
    @Override
    public int add(Long chatId, Long linkId) {
        return jdbcTemplate.update(INSERT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
                ps.setLong(2, linkId);
            }
        });
    }

    @Transactional
    @Override
    public int remove(Long chatId, Long linkId) {
        return jdbcTemplate.update(DELETE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
                ps.setLong(2, linkId);
            }
        });
    }

    @Transactional
    @Override
    public Subscription find(Long chatId, Long linkId) {
        return jdbcTemplate.query(SELECT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
                ps.setLong(2, linkId);
            }
        }, new SubscriptionRowMapper()).stream().findFirst().orElse(null);
    }

}