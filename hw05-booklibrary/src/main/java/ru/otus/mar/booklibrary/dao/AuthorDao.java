package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author create(Author author);

    Author update(Author author);

    void deleteById(long id);

    List<Author> getAll();

    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);

    List<Author> getByFilter(String filter);
}
