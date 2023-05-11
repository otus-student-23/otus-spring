package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.BookComment;

import java.util.UUID;

public interface BookCommentDao {

    BookComment insert(BookComment comment);

    BookComment update(BookComment comment);

    void delete(BookComment comment);

    BookComment getById(UUID id);
}
