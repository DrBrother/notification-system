package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;

@RestController
@Tag(name = "default")
public class LinkController {

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {@Content(schema = @Schema(implementation = ListLinksResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
    @GetMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLinks(@RequestHeader(name = "Tg-Chat-Id", required = true) Integer id) {
        return new ResponseEntity<>(new ListLinksResponse(null, null), HttpStatus.OK);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {@Content(schema = @Schema(implementation = LinkResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
    @PostMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLink(@RequestHeader(name = "Tg-Chat-Id", required = true) Integer id,
                                     @Valid @RequestBody AddLinkRequest request) {
        return new ResponseEntity<>(new LinkResponse(null, null), HttpStatus.OK);
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {@Content(schema = @Schema(implementation = LinkResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})})
    @DeleteMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteLink(@RequestHeader(name = "Tg-Chat-Id", required = true) Integer id,
                                        @Valid @RequestBody RemoveLinkRequest request) {
        return new ResponseEntity<>(new LinkResponse(null, null), HttpStatus.OK);
    }

}