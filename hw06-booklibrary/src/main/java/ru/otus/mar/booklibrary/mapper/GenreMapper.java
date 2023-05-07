package ru.otus.mar.booklibrary.mapper;

import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Genre;

public interface GenreMapper {

    GenreDto toDto(Genre genre);

    Genre fromDto(GenreDto genre);
}
