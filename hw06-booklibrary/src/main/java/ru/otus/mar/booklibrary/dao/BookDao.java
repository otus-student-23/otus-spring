package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDao {

    Book insert(Book book);

    Book update(Book book);

    void delete(Book book);

    List<Book> getAll();

    Book getById(UUID id);

    Optional<Book> getByNameAndAuthor(String name, Author author);

    List<Book> getByName(String name);
}
