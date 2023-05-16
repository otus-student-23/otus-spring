package ru.otus.mar.booklibrary.dto;

import java.util.UUID;

public record BookCommentDto(UUID id, BookDto book, String comment) {

    public BookCommentDto(BookDto book, String name) {
        this(null, book, name);
    }
}
