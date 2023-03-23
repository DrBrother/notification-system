package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GitHubResponse(Long id, String full_name, OffsetDateTime created_at, OffsetDateTime updated_at,
                             OffsetDateTime pushed_at) {
}