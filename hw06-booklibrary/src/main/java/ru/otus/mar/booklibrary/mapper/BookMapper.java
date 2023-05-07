package ru.otus.mar.booklibrary.mapper;

import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Book;

public interface BookMapper {

    BookDto toDto(Book book);

    Book fromDto(BookDto book);
}
