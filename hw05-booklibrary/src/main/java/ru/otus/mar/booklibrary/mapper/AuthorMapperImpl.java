package ru.otus.mar.booklibrary.mapper;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.dto.AuthorDto;

@Service
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.name());
    }

    @Override
    public Author fromDto(AuthorDto author) {
        return new Author(author.name());
    }
}
