package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    void create(GenreDto genre);

    void delete(GenreDto genre);

    List<GenreDto> getAll();

    Optional<GenreDto> getByName(String name);

    List<GenreDto> getByFilter(String filter);
}
