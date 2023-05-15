package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponse(Long id,
                             @JsonProperty(value = "full_name") String fullName,
                             @JsonProperty(value = "created_at") OffsetDateTime createdTime,
                             @JsonProperty(value = "updated_at") OffsetDateTime updatedTime,
                             @JsonProperty(value = "pushed_at") OffsetDateTime pushedTime) {
}
