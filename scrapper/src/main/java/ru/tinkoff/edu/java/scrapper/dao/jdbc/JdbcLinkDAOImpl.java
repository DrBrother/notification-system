package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.jdbc.rowMapper.LinkRowMapper;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class JdbcLinkDAOImpl implements LinkDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_URL = "SELECT * FROM link WHERE url = (?)";
    private static final String SELECT_BY_CHAT = "SELECT * FROM link JOIN subscription s on link.id = s.link_id WHERE chat_id = (?)";
    private static final String SELECT_OLDER_THAN = "SELECT * FROM link WHERE checkTime <= (?)";
    private static final String INSERT_AND_RETURN = "INSERT INTO link (url) VALUES (?) RETURNING *";
    private static final String UPDATE_LINK = "UPDATE link SET updateTime = (?), checkTime = NOW() WHERE id = (?)";

    @Transactional
    @Override
    public Link add(URL link) {
        return jdbcTemplate.query(INSERT_AND_RETURN, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, link.toString());
            }
        }, new LinkRowMapper()).stream().findFirst().orElse(null);
    }

    @Transactional
    @Override
    public int update(Link link) {
        return jdbcTemplate.update(UPDATE_LINK, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setTimestamp(1, Timestamp.valueOf(link.getUpdateTime().toLocalDateTime()));
                ps.setLong(2, link.getId());
            }
        });
    }

    @Transactional
    @Override
    public Link findByUrl(URL link) {
        return jdbcTemplate.query(SELECT_BY_URL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, link.toString());
            }
        }, new LinkRowMapper()).stream().findFirst().orElse(null);
    }

    @Transactional
    @Override
    public List<Link> findAllByChat(Long chatId) {
        return jdbcTemplate.query(SELECT_BY_CHAT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, chatId);
            }
        }, new LinkRowMapper());
    }

    @Transactional
    @Override
    public List<Link> findOlderThan(OffsetDateTime checkTime) {
        return jdbcTemplate.query(SELECT_OLDER_THAN, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setTimestamp(1, Timestamp.valueOf(checkTime.toLocalDateTime()));
            }
        }, new LinkRowMapper());
    }

}