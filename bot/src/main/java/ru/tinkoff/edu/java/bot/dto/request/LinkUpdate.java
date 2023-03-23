package ru.tinkoff.edu.java.bot.dto.request;

import jakarta.validation.constraints.NotNull;

public record LinkUpdate(@NotNull Integer id, @NotNull String url, @NotNull String description, @NotNull Integer[] tgChatIds) {
}