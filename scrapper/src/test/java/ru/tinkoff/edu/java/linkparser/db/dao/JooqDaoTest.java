package ru.tinkoff.edu.java.linkparser.db.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.db.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqChatDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqLinkDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqSubscriptionDAOImpl;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfiguration.class)
public class JooqDaoTest extends IntegrationEnvironment implements TestDAO {

    @Autowired
    private JooqChatDAOImpl chatDao;
    @Autowired
    private JooqLinkDAOImpl linkDao;
    @Autowired
    private JooqSubscriptionDAOImpl subscriptionDao;

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void addAndFindLinkTest() {
        Link addedLink = linkDao.add(new URL("https://github.com/DrBrother/tinkoff"));

        Link findedLink = linkDao.findByUrl(new URL("https://github.com/DrBrother/tinkoff"));

        assertThat(addedLink.getId()).isEqualTo(findedLink.getId());
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void findAllLinksByChatTest() {
        long randomLong = new Random().nextLong();

        chatDao.add(randomLong);
        Link linkOne = linkDao.add(new URL("https://github.com/DrBrother/tracker"));
        Link linkTwo = linkDao.add(new URL("https://github.com/DrBrother/scrapper"));
        subscriptionDao.add(randomLong, linkOne.getId());
        subscriptionDao.add(randomLong, linkTwo.getId());

        assertThat(linkDao.findAllByChat(randomLong)).hasSize(2);
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void subscriptionTest() {
        long randomLong = new Random().nextLong();
        long randomLong1 = new Random().nextLong();

        chatDao.add(randomLong);
        chatDao.add(randomLong1);
        Link link = linkDao.add(new URL("https://github.com/DrBrother/bot"));
        subscriptionDao.add(randomLong, link.getId());
        subscriptionDao.add(randomLong1, link.getId());

        assertThat(chatDao.findAllByLink(link.getId())).hasSize(2);

        subscriptionDao.remove(randomLong, link.getId());

        assertThat(chatDao.findAllByLink(link.getId())).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void updateLinkTest() {
        Link addedLink = linkDao.add(new URL("https://github.com/DrBrother/parser"));
        Link link = new Link(addedLink.getId(), new URL("https://github.com/DrBrother/parser"), OffsetDateTime.now().plusHours(1L), OffsetDateTime.now().plusHours(2L));
        linkDao.update(link);
        Link updatedLink = linkDao.findByUrl(link.getUrl());

        assertThat(link).isNotEqualTo(updatedLink);
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void findOlderThanLinkTest() {
        linkDao.add(new URL("https://github.com/DrBrother/link-parser"));
        assertThat(linkDao.findOlderThan(OffsetDateTime.now().plusHours(2))).hasSizeGreaterThan(0);
    }

}