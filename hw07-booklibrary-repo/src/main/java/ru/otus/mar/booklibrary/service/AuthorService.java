package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorDto create(AuthorDto author);

    AuthorDto update(AuthorDto author);

    void delete(AuthorDto author);

    List<AuthorDto> getAll();

    Optional<AuthorDto> getByName(String name);
}
