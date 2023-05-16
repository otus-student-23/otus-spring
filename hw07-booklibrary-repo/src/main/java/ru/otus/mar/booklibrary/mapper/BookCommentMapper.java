package ru.otus.mar.booklibrary.mapper;

import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.model.BookComment;

public interface BookCommentMapper {

    BookCommentDto toDto(BookComment author);

    BookComment fromDto(BookCommentDto author);
}
