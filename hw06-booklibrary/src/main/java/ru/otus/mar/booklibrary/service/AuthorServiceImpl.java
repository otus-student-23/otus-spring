package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dao.AuthorDao;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    private final AuthorMapper mapper;

    @Override
    @Transactional
    public AuthorDto create(AuthorDto author) {
        return author.id() != null
                ? author
                : mapper.toDto(dao.getByName(author.name()).orElseGet(() -> dao.insert(mapper.fromDto(author))));
    }

    @Override
    @Transactional
    public AuthorDto update(AuthorDto author) {
        return mapper.toDto(dao.update(mapper.fromDto(author)));
    }

    @Override
    @Transactional
    public void delete(AuthorDto author) {
        dao.delete(mapper.fromDto(author));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return dao.getAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> getByName(String name) {
        return dao.getByName(name).map(mapper::toDto);
    }
}
