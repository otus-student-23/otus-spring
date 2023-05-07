package ru.otus.mar.booklibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class BookDto {

    private UUID id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

    public BookDto(String name, AuthorDto author, GenreDto genre) {
        this(null, name, author, genre);
    }
}