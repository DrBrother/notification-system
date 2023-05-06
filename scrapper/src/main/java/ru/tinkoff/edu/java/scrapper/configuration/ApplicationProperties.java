package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.dao.DatabaseAccessType;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;

import java.util.Objects;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationProperties(@NotNull String test,
                                    @NotNull Clients clients,
                                    @NotNull Scheduler scheduler,
                                    Integer linkCheckPeriodMinutes,
                                    @NotNull DatabaseAccessType databaseAccessType) {

    private static final String STACKOVERFLOW = "https://api.stackexchange.com/2.3";
    private static final String GITHUB = "https://api.github.com";

    @Override
    public Integer linkCheckPeriodMinutes() {
        return Objects.requireNonNullElse(linkCheckPeriodMinutes, 30);
    }

    public record Clients(String stackoverflow, String github, @NotNull String bot) {
        @Override
        public String stackoverflow() {
            return Objects.requireNonNullElse(stackoverflow, STACKOVERFLOW);
        }

        @Override
        public String github() {
            return Objects.requireNonNullElse(github, GITHUB);
        }
    }

}