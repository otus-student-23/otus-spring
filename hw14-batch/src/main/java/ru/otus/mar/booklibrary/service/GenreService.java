package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.model.document.GenreDoc;
import ru.otus.mar.booklibrary.model.entity.Genre;

public interface GenreService {

    GenreDoc buildDocument(Genre genre);
}
