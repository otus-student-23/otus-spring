package ru.otus.mar.booklibrary.service;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dao.AuthorDao;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorDao authorDao, AuthorMapper authorMapper) {
        this.authorDao = authorDao;
        this.authorMapper = authorMapper;
    }

    @Override
    public void create(AuthorDto author) {
        authorDao.create(authorMapper.fromDto(author));
    }

    @Override
    public void delete(AuthorDto author) {
        authorDao.deleteById(authorDao.getByName(author.name()).get().id());
    }

    @Override
    public List<AuthorDto> getAll() {
        return authorDao.getAll().stream().map(authorMapper::toDto).toList();
    }

    @Override
    public Optional<AuthorDto> getByName(String name) {
        return authorDao.getByName(name).map(authorMapper::toDto);
    }

    @Override
    public List<AuthorDto> getByFilter(String filter) {
        return authorDao.getByFilter(filter).stream().map(authorMapper::toDto).toList();
    }
}
