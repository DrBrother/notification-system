package ru.tinkoff.edu.java.scrapper.client;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

public interface GithubClient {
    Mono<GitHubResponse> fetchRepository(String name, String repo);
}