package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;

import java.util.List;

public interface BookCommentService {

    BookCommentDto create(BookCommentDto comment);

    BookCommentDto update(BookCommentDto comment);

    void delete(BookCommentDto comment);

    List<BookCommentDto> getByBook(BookDto book);
}
