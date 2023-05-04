package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    void create(AuthorDto author);

    void delete(AuthorDto author);

    List<AuthorDto> getAll();

    Optional<AuthorDto> getByName(String name);

    List<AuthorDto> getByFilter(String filter);
}
