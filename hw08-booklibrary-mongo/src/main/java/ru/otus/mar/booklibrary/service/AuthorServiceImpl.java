package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repo;

    private final AuthorMapper mapper;

    @Override
    public AuthorDto create(AuthorDto author) {
        return mapper.toDto(repo.findByName(author.name()).orElseGet(() -> repo.save(mapper.fromDto(author))));
    }

    @Override
    public AuthorDto update(AuthorDto author) {
        return mapper.toDto(repo.save(mapper.fromDto(author)));
    }

    @Override
    public void delete(AuthorDto author) {
        repo.deleteById(author.id());
    }

    @Override
    public List<AuthorDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<AuthorDto> getByName(String name) {
        return repo.findByName(name).map(mapper::toDto);
    }
}
