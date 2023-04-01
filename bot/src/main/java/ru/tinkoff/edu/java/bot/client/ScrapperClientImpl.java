package ru.tinkoff.edu.java.bot.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;

@AllArgsConstructor
public class ScrapperClientImpl implements ScrapperClient {

    private static final String SLASH = "/";
    private static final String TG_CHAT = "/tg-chat";
    private static final String LINKS = "/links";
    private static final String TG_CHAT_ID = "Tg-Chat-Id";

    private final WebClient client;

    @Override
    public Mono<ResponseEntity> registerChat(Long id) {
        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .path(TG_CHAT + SLASH + id)
                        .build())
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse.bodyToMono(ApiErrorResponse.class)
//                                пока что ловлю Exception, потому что в контроллерах заглушки
                                .flatMap(apiErrorResponse -> Mono.error(new Exception(apiErrorResponse.description())));
                    }
                    return clientResponse.bodyToMono(ResponseEntity.class);
                });
    }

    @Override
    public Mono<ResponseEntity> deleteChat(Long id) {
        return client.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(TG_CHAT + SLASH + id)
                        .build())
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse.bodyToMono(ApiErrorResponse.class)
//                                пока что ловлю Exception, потому что в контроллерах заглушки
                                .flatMap(apiErrorResponse -> Mono.error(new Exception(apiErrorResponse.description())));
                    }
                    return clientResponse.bodyToMono(ResponseEntity.class);
                });
    }

    @Override
    public Mono<ListLinksResponse> getLinks(Long id) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(LINKS)
                        .build())
                .header(TG_CHAT_ID, String.valueOf(id))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse.bodyToMono(ApiErrorResponse.class)
//                                пока что ловлю Exception, потому что в контроллерах заглушки
                                .flatMap(apiErrorResponse -> Mono.error(new Exception(apiErrorResponse.description())));
                    }
                    return clientResponse.bodyToMono(ListLinksResponse.class);
                });
    }

    @Override
    public Mono<LinkResponse> addLink(Long id, AddLinkRequest request) {
        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .path(LINKS)
                        .build())
                .header(TG_CHAT_ID, String.valueOf(id))
                .bodyValue(request)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse.bodyToMono(ApiErrorResponse.class)
//                                пока что ловлю Exception, потому что в контроллерах заглушки
                                .flatMap(apiErrorResponse -> Mono.error(new Exception(apiErrorResponse.description())));
                    }
                    return clientResponse.bodyToMono(LinkResponse.class);
                });
    }

    @Override
    public Mono<LinkResponse> deleteLink(Long id, RemoveLinkRequest request) {
        return client.method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder
                        .path(LINKS)
                        .build())
                .header(TG_CHAT_ID, String.valueOf(id))
                .bodyValue(request)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                        return clientResponse.bodyToMono(ApiErrorResponse.class)
//                                пока что ловлю Exception, потому что в контроллерах заглушки
                                .flatMap(apiErrorResponse -> Mono.error(new Exception(apiErrorResponse.description())));
                    }
                    return clientResponse.bodyToMono(LinkResponse.class);
                });
    }

}