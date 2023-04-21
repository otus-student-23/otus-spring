package ru.otus.mar.testsystem.exception;

public class QuestionsDeserializeException extends RuntimeException {

    public QuestionsDeserializeException(String message, Throwable error) {
        super(message, error);
    }
}
