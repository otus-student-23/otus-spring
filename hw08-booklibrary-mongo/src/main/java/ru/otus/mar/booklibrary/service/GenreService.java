package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.GenreDto;

import java.util.Optional;

public interface GenreService extends AbstractCrudService<GenreDto, String> {

    Optional<GenreDto> getByName(String name);
}
