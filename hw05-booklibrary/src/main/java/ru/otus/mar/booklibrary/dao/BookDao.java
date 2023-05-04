package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Book create(Book book);

    Book update(Book book);

    void deleteById(long id);

    List<Book> getAll();

    Optional<Book> getById(long id);

    Optional<Book> getByNameAndAuthor(String name, Author author);

    List<Book> getByFilter(String filter);
}
