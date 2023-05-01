package ru.otus.mar.booklibrary.exception;

public class FilterExpressionParserException extends RuntimeException {

    public FilterExpressionParserException(String message, Throwable error) {
        super(message, error);
    }
}
