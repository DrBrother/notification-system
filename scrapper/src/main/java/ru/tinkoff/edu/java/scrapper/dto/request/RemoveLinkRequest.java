package ru.tinkoff.edu.java.scrapper.dto.request;

import jakarta.validation.constraints.NotNull;

public record RemoveLinkRequest(@NotNull String link) {
}