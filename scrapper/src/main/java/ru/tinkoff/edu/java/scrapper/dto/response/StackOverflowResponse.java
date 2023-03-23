package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record StackOverflowResponse(Item[] items) {
    private record Item(OffsetDateTime creation_date, OffsetDateTime last_edit_date, boolean is_answered,
                        Long question_id) {
    }
}