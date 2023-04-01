package ru.tinkoff.edu.java.bot.client;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;

public interface ScrapperClient {

    Mono<ResponseEntity> registerChat(Long id);

    Mono<ResponseEntity> deleteChat(Long id);

    Mono<ListLinksResponse> getLinks(Long id);

    Mono<LinkResponse> addLink(Long id, AddLinkRequest request);

    Mono<LinkResponse> deleteLink(Long id, RemoveLinkRequest request);

}
