package ru.tinkoff.edu.java.scrapper.entity.jdbc.rowMapper;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class LinkRowMapper implements RowMapper<Link> {

    @SneakyThrows
    @Override
    public Link mapRow(ResultSet rs, int rowNum) {
        Link link = new Link();
        link.setId(rs.getLong("id"));
        link.setUrl(new URL(rs.getString("url")));
        link.setCheckTime(OffsetDateTime.of(rs.getObject("checkTime", LocalDateTime.class), ZoneOffset.UTC));
        link.setUpdateTime(OffsetDateTime.of(rs.getObject("updateTime", LocalDateTime.class), ZoneOffset.UTC));
        return link;
    }

}