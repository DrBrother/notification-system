package ru.tinkoff;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class JooqCodegen {
    private static final PostgreSQLContainer<?> DB_CONTAINER;
    private static final String MASTER_PATH = "migrations/master.yaml";

    static {
        DB_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        DB_CONTAINER.start();
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(DB_CONTAINER.getDriverClassName());
            dataSource.setUrl(DB_CONTAINER.getJdbcUrl());
            dataSource.setUsername(DB_CONTAINER.getUsername());
            dataSource.setPassword(DB_CONTAINER.getPassword());
            Connection connection = dataSource.getConnection();
            Path path = new File(".").toPath().toAbsolutePath().normalize();
            liquibase.database.Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase(MASTER_PATH,
                    new DirectoryResourceAccessor(path), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver(DB_CONTAINER.getDriverClassName())
                        .withUrl(DB_CONTAINER.getJdbcUrl())
                        .withUser(DB_CONTAINER.getUsername())
                        .withPassword(DB_CONTAINER.getPassword()))
                .withGenerator(new Generator()
                        .withGenerate(new Generate()
                                .withGeneratedAnnotation(true)
                                .withGeneratedAnnotationDate(false)
                                .withNullableAnnotation(true)
                                .withNullableAnnotationType("org.jetbrains.annotations.Nullable")
                                .withNonnullAnnotation(true)
                                .withNonnullAnnotationType("org.jetbrains.annotations.NotNull")
                                .withJpaAnnotations(false)
                                .withValidationAnnotations(true)
                                .withSpringAnnotations(true)
                                .withConstructorPropertiesAnnotation(true)
                                .withConstructorPropertiesAnnotationOnPojos(true)
                                .withConstructorPropertiesAnnotationOnRecords(true)
                                .withFluentSetters(false)
                                .withDaos(false)
                                .withPojos(false))
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withInputSchema("public"))
                        .withTarget(new Target()
                                .withPackageName("ru.tinkoff.edu.java.scrapper.entity.jooq")
                                .withDirectory("scrapper/src/main/java")));

        GenerationTool.generate(configuration);
    }

}