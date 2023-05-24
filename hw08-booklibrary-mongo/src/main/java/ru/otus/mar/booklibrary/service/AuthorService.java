package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.AuthorDto;

import java.util.Optional;

public interface AuthorService extends AbstractCrudService<AuthorDto, String> {

    Optional<AuthorDto> getByName(String name);
}
