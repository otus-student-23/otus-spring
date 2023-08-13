package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.model.document.BookDoc;
import ru.otus.mar.booklibrary.model.entity.Book;

public interface BookService {

    BookDoc buildDocument(Book book);
}
