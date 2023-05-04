package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Genre create(Genre genre);

    Genre update(Genre genre);

    void deleteById(long id);

    List<Genre> getAll();

    Optional<Genre> getById(long id);

    Optional<Genre> getByName(String name);

    List<Genre> getByFilter(String filter);
}
