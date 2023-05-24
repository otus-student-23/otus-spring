package ru.otus.mar.booklibrary.dto;

public record AuthorDto (String id, String name) {

    public AuthorDto(String name) {
        this(null, name);
    }
}