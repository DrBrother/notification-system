package ru.tinkoff.edu.java.scrapper.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddLinkRequest(@NotNull String link) {
}