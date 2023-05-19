package ru.otus.mar.booklibrary.dto;

import java.util.UUID;

public record GenreDto (UUID id, String name) {

    public GenreDto(String name) {
        this(null, name);
    }
}