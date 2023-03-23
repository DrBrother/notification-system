package ru.tinkoff.edu.java.scrapper.exception;

public class LinkNotFoundException extends Exception {
    public LinkNotFoundException() {
    }

    public LinkNotFoundException(String message) {
        super(message);
    }

    public LinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkNotFoundException(Throwable cause) {
        super(cause);
    }

    public LinkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}