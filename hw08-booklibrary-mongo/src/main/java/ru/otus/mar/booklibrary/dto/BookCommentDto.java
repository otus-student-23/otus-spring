package ru.otus.mar.booklibrary.dto;

public record BookCommentDto(String id, BookDto book, String comment) {

    public BookCommentDto(BookDto book, String name) {
        this(null, book, name);
    }
}
