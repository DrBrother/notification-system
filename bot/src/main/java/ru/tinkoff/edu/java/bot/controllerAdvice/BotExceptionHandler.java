package ru.tinkoff.edu.java.bot.controllerAdvice;

import ch.qos.logback.core.helpers.ThrowableToStringArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.common.exception.constants.BotExceptionConstants;

@RestControllerAdvice
public class BotExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ApiErrorResponse> handleEmptyField(Exception e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, BotExceptionConstants.INCORRECT_PARAMETERS);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUncheckedException(Exception e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, BotExceptionConstants.SERVER_ERROR);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiErrorResponse createApiErrorResponse(Exception e, BotExceptionConstants constants) {
        return new ApiErrorResponse(
                constants.getDescription(),
                constants.getStatus().toString(),
                e.getClass().getName(),
                e.getMessage(),
                ThrowableToStringArray.convert(e)
        );
    }

}