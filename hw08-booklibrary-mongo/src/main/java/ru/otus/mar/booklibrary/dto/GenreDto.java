package ru.otus.mar.booklibrary.dto;

public record GenreDto (String id, String name) {

    public GenreDto(String name) {
        this(null, name);
    }
}