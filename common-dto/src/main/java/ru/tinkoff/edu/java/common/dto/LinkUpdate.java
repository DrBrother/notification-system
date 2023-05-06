package ru.tinkoff.edu.java.common.dto;

import jakarta.validation.constraints.NotNull;

public record LinkUpdate(@NotNull Long id, @NotNull String url, @NotNull String description, @NotNull Long[] tgChatIds) {
}