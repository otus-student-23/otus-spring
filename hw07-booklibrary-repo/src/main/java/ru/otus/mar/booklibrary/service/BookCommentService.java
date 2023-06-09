package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;

import java.util.List;
import java.util.UUID;

public interface BookCommentService extends AbstractCrudService<BookCommentDto, UUID> {

    List<BookCommentDto> getByBook(BookDto book);
}
