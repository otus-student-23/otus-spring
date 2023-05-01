package ru.otus.mar.booklibrary.mapper;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Genre;

@Service
public class GenreMapperImpl implements GenreMapper {
    @Override
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.name());
    }

    @Override
    public Genre fromDto(GenreDto genre) {
        return new Genre(genre.name());
    }
}
