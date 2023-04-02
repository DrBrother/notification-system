package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubClientImpl implements GithubClient {

    private static final String REPOS = "/repos/";
    private static final String SLASH = "/";

    @Qualifier("github")
    private final WebClient client;

    @Override
    public Mono<GitHubResponse> fetchRepository(String name, String repo) {
        return client
                .get()
                .uri(REPOS + name + SLASH + repo)
                .retrieve()
                .bodyToMono(GitHubResponse.class)
                .onErrorResume(throwable -> {
                    log.error("Exception: {}", throwable.getMessage());
                    return Mono.empty();
                });
    }

}