package ru.tinkoff.edu.java.bot.dto.response;

public record ListLinksResponse(LinkResponse[] links, Integer size) {
}