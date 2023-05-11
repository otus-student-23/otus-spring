package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dao.GenreDao;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    private final GenreMapper mapper;

    @Override
    @Transactional
    public GenreDto create(GenreDto genre) {
        return genre.id() != null
                ? genre
                : mapper.toDto(dao.getByName(genre.name()).orElseGet(() -> dao.insert(mapper.fromDto(genre))));
    }

    @Override
    @Transactional
    public GenreDto update(GenreDto genre) {
        return mapper.toDto(dao.update(mapper.fromDto(genre)));
    }

    @Override
    @Transactional
    public void delete(GenreDto genre) {
        dao.delete(mapper.fromDto(genre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAll() {
        return dao.getAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> getByName(String name) {
        return dao.getByName(name).map(mapper::toDto);
    }
}
