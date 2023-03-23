package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;

import java.util.Objects;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, String stackoverflow, String github,
                                @NotNull Scheduler scheduler) {

    private static final String STACKOVERFLOW = "https://api.stackexchange.com/2.3";
    private static final String GITHUB = "https://api.github.com";

    @Override
    public String stackoverflow() {
        return Objects.requireNonNullElse(stackoverflow, STACKOVERFLOW);
    }

    @Override
    public String github() {
        return Objects.requireNonNullElse(github, GITHUB);
    }

}