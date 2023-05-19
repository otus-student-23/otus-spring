package ru.otus.mar.booklibrary.dto;

import java.util.UUID;

public record AuthorDto (UUID id, String name) {

    public AuthorDto(String name) {
        this(null, name);
    }
}