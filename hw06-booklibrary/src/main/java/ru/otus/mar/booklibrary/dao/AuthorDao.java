package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorDao {

    Author insert(Author author);

    Author update(Author author);

    void delete(Author author);

    List<Author> getAll();

    Author getById(UUID id);

    Optional<Author> getByName(String name);
}
