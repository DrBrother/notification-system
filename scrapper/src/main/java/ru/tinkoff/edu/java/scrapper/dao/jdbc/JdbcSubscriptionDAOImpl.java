package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.SubscriptionDAO;
import ru.tinkoff.edu.java.scrapper.entity.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jdbc")
@Repository
public class JdbcSubscriptionDAOImpl implements SubscriptionDAO {

    private final JdbcTemplate jdbcTemplate;

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
        }, this::mapRow).stream().findFirst().orElse(null);
    }

    @SneakyThrows
    public Subscription mapRow(ResultSet rs, int rowNum) {
        Subscription subscription = new Subscription();
        subscription.setChatId(rs.getLong("chat_id"));
        subscription.setLinkId(rs.getLong("link_id"));
        return subscription;
    }

}