package ru.tinkoff.edu.java.linkparser.db;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcChatDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcLinkDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcSubscriptionDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqChatDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqLinkDAOImpl;
import ru.tinkoff.edu.java.scrapper.dao.jooq.JooqSubscriptionDAOImpl;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class IntegrationEnvironment {
    protected static final PostgreSQLContainer<?> SQL_CONTAINER;
    private static final String MASTER_PATH = "migrations/master.yaml";

    @Configuration
    public static class IntegrationEnvironmentConfiguration {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(SQL_CONTAINER.getJdbcUrl())
                    .username(SQL_CONTAINER.getUsername())
                    .password(SQL_CONTAINER.getPassword())
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        public JdbcChatDAOImpl jdbcChatDao() {
            return new JdbcChatDAOImpl(jdbcTemplate());
        }

        @Bean
        public JdbcLinkDAOImpl jdbcLinkDao() {
            return new JdbcLinkDAOImpl(jdbcTemplate());
        }

        @Bean
        public JdbcSubscriptionDAOImpl jdbcSubscriptionDAO() {
            return new JdbcSubscriptionDAOImpl(jdbcTemplate());
        }

        @Bean
        public DSLContext dslContext() {
            return DSL.using(dataSource(), SQLDialect.POSTGRES);
        }

        @Bean
        public JooqChatDAOImpl jooqChatDao() {
            return new JooqChatDAOImpl(dslContext());
        }

        @Bean
        public JooqLinkDAOImpl jooqLinkDAO() {
            return new JooqLinkDAOImpl(dslContext());
        }

        @Bean
        public JooqSubscriptionDAOImpl jooqSubscriptionDAO() {
            return new JooqSubscriptionDAOImpl(dslContext());
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager() {
            JdbcTransactionManager transactionManager = new JdbcTransactionManager();
            transactionManager.setDataSource(dataSource());
            return transactionManager;
        }
    }

    static {
        SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        SQL_CONTAINER.start();

        try {
            Connection connection = getConnection();
            Path path = new File("../").toPath().toAbsolutePath().normalize();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase(MASTER_PATH,
                    new DirectoryResourceAccessor(path), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                SQL_CONTAINER.getJdbcUrl(),
                SQL_CONTAINER.getUsername(),
                SQL_CONTAINER.getPassword()
        );
    }

}