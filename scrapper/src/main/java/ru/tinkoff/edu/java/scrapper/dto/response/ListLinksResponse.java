package ru.tinkoff.edu.java.scrapper.dto.response;

public record ListLinksResponse(LinkResponse[] links, Integer size) {
}