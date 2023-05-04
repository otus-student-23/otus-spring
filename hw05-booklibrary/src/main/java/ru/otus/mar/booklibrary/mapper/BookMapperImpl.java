package ru.otus.mar.booklibrary.mapper;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

@Service
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        return new BookDto(book.getName(), book.getAuthor().name(), book.getGenre().name());
    }

    @Override
    public Book fromDto(BookDto book) {
        return new Book(book.name(), new Author(book.author()), new Genre(book.genre()));
    }
}
