package ru.otus.mar.booklibrary.model;

public record Genre (Long id, String name) {

    public Genre(String name) {
        this(null, name);
    }
}
