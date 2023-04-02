package ru.tinkoff.edu.java.bot.controllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum BotExceptionConstants {

    //    400
    INCORRECT_PARAMETERS("Некорректные параметры запроса", HttpStatus.BAD_REQUEST),
    //    500
    SERVER_ERROR("Сервер сломался", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String description;
    private final HttpStatus status;

}