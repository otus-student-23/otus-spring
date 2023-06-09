package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto create(BookDto book);

    BookDto update(BookDto book);

    void delete(BookDto book);

    List<BookDto> getAll();

    Optional<BookDto> getByNameAndAuthor(String name, AuthorDto author);

    List<BookDto> getByName(String name);
}
