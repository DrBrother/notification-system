package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubEvent(Long id, String type, Actor actor, Payload payload,
                          @JsonProperty(value = "created_at") OffsetDateTime createTime) {

    public record Actor(String login) {

    }

    public record Payload(Commit[] commits, @JsonProperty(value = "pull_request") PullRequest pullRequest,
                          Comment comment,
                          // for CREATE_EVENT
                          String reference, @JsonProperty(value = "ref_type") String referenceType) {

        // for PUSH_EVENT
        public record Commit(@JsonProperty(value = "sha") String commitHash, String message) {

        }

        // for PULL_REQUEST_REVIEW_COMMENT_EVENT
        public record Comment(String url, @JsonProperty(value = "body") String comment) {

        }

        // for PULL_REQUEST_REVIEW_COMMENT_EVENT
        public record PullRequest(String url, String title) {

        }
    }

}
