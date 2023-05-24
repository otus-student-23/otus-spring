package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;

import java.util.List;

public interface BookCommentService extends AbstractCrudService<BookCommentDto, String> {

    List<BookCommentDto> getByBook(BookDto book);
}
