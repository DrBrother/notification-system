package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowResponse(Item[] items) {
    public record Item(@JsonProperty(value = "creation_date") OffsetDateTime creationTime,
                        @JsonProperty(value = "last_edit_date") OffsetDateTime lastEditTime,
                        @JsonProperty(value = "is_answered") boolean isAnswered,
                        @JsonProperty(value = "question_id") Long questionId) {
    }
}