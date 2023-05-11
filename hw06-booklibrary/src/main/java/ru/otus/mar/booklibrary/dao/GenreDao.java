package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreDao {

    Genre insert(Genre genre);

    Genre update(Genre genre);

    void delete(Genre genre);

    List<Genre> getAll();

    Genre getById(UUID id);

    Optional<Genre> getByName(String name);
}
