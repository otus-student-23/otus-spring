package ru.otus.mar.booklibrary.mapper;

import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.dto.AuthorDto;

public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author fromDto(AuthorDto author);
}
