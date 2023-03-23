package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class StackOverflowClientImpl implements StackOverflowClient {

    private static final String QUESTION = "/questions/";
    private static final String SITE = "site";
    private static final String STACKOVERFLOW = "stackoverflow";

    @Qualifier("stackoverflow")
    private final WebClient client;

    @Override
    public Mono<StackOverflowResponse> fetchQuestion(int questionId) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(QUESTION + questionId)
                        .queryParam(SITE, STACKOVERFLOW)
                        .build())
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .onErrorResume(throwable -> {
                    log.error("Exception: {}", throwable.getMessage());
                    return Mono.empty();
                });
    }

}