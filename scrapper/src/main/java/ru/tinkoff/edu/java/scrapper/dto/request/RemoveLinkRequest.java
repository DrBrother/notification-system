package ru.tinkoff.edu.java.scrapper.dto.request;

import jakarta.validation.constraints.NotNull;

import java.net.URL;

public record RemoveLinkRequest(@NotNull URL link) {
}