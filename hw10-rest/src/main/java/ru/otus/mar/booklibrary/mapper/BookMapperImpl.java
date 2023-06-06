package ru.otus.mar.booklibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Book;

@RequiredArgsConstructor
@Service
public class BookMapperImpl implements BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                authorMapper.toDto(book.getAuthor()),
                genreMapper.toDto(book.getGenre())
        );
    }

    @Override
    public Book fromDto(BookDto book) {
        return new Book(
                book.getId(),
                book.getName(),
                authorMapper.fromDto(book.getAuthor()),
                genreMapper.fromDto(book.getGenre()),
                null
        );
    }
}
