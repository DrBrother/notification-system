package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

public interface BotClient {

    Mono<ResponseEntity> pullLinks(LinkUpdate linkUpdate);

}