package ru.tinkoff.edu.java.scrapper.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

public interface GithubClient {
    Mono<GitHubResponse> fetchRepository(String name, String repo);

    Flux<GitHubEvent> fetchRepositoryEvents(String user, String repository);
}