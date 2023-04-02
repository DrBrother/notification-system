package ru.tinkoff.edu.java.scrapper.client;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

public interface StackOverflowClient {
    Mono<StackOverflowResponse> fetchQuestion(int questionId);
}