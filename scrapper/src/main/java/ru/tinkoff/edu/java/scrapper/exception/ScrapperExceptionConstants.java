package ru.tinkoff.edu.java.scrapper.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ScrapperExceptionConstants {

    //    400
    INCORRECT_PARAMETERS("Некорректные параметры запроса", HttpStatus.BAD_REQUEST),
    //    404
    CHAT_NOT_FOUND("Чат не существует", HttpStatus.NOT_FOUND),
    LINK_NOT_FOUND("Ссылка не найдена", HttpStatus.NOT_FOUND),
    //    500
    SERVER_ERROR("Сервер сломался", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String description;
    private final HttpStatus status;

}