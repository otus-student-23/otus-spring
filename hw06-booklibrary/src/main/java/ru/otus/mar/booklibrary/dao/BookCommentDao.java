package ru.otus.mar.booklibrary.dao;

import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;

import java.util.List;
import java.util.UUID;

public interface BookCommentDao {

    BookComment insert(BookComment comment);

    BookComment update(BookComment comment);

    void delete(BookComment comment);

    void deleteByBook(Book book);

    BookComment getById(UUID id);

    List<BookComment> getByBook(Book book);
}
