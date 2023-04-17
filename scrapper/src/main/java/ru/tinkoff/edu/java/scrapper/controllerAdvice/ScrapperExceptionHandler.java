package ru.tinkoff.edu.java.scrapper.controllerAdvice;

import ch.qos.logback.core.helpers.ThrowableToStringArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.common.exception.ChatNotFoundException;
import ru.tinkoff.edu.java.common.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.common.exception.SubscriptionNotFountException;
import ru.tinkoff.edu.java.common.exception.constants.ScrapperExceptionConstants;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;

@RestControllerAdvice
public class ScrapperExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ApiErrorResponse> handleEmptyField(Exception e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, ScrapperExceptionConstants.INCORRECT_PARAMETERS);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotFound(LinkNotFoundException e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, ScrapperExceptionConstants.LINK_NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFound(ChatNotFoundException e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, ScrapperExceptionConstants.CHAT_NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUncheckedException(Exception e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, ScrapperExceptionConstants.SERVER_ERROR);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SubscriptionNotFountException.class)
    public ResponseEntity<ApiErrorResponse> handleSubscriptionNotFountException(SubscriptionNotFountException e) {
        ApiErrorResponse apiErrorResponse = createApiErrorResponse(e, ScrapperExceptionConstants.SUBSCRIPTION_NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiErrorResponse createApiErrorResponse(Exception e, ScrapperExceptionConstants constants) {
        return new ApiErrorResponse(
                constants.getDescription(),
                constants.getStatus().toString(),
                e.getClass().getName(),
                e.getMessage(),
                ThrowableToStringArray.convert(e)
        );
    }

}