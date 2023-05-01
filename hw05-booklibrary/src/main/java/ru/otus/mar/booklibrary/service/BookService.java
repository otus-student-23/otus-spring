package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void create(BookDto book);

    void delete(BookDto book);

    List<BookDto> getAll();

    Optional<BookDto> getByNameAndAuthor(String name, String author);

    List<BookDto> getByFilter(String filter);
}
