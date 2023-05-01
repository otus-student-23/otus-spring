package ru.otus.mar.booklibrary.service;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dao.GenreDao;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.mapper.GenreMapper;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreDao genreDao, GenreMapper genreMapper) {
        this.genreDao = genreDao;
        this.genreMapper = genreMapper;
    }

    @Override
    public void create(GenreDto genre) {
        genreDao.create(genreMapper.fromDto(genre));
    }

    @Override
    public void delete(GenreDto genre) {
        genreDao.deleteById(genreDao.getByName(genre.name()).get().id());
    }

    @Override
    public List<GenreDto> getAll() {
        return genreDao.getAll().stream().map(genreMapper::toDto).toList();
    }

    @Override
    public Optional<GenreDto> getByName(String name) {
        return genreDao.getByName(name).map(genreMapper::toDto);
    }

    @Override
    public List<GenreDto> getByFilter(String filter) {
        return genreDao.getByFilter(filter).stream().map(genreMapper::toDto).toList();
    }
}
