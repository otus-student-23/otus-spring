package ru.otus.mar.booklibrary.model;

public record Author (Long id, String name) {

    public Author(String name) {
        this(null, name);
    }
}
