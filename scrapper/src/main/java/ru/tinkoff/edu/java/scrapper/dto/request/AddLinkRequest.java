package ru.tinkoff.edu.java.scrapper.dto.request;

import jakarta.validation.constraints.NotNull;

import java.net.URL;

public record AddLinkRequest(@NotNull URL link) {
}