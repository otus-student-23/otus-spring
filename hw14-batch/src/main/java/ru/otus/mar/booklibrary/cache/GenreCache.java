package ru.otus.mar.booklibrary.cache;

import ru.otus.mar.booklibrary.model.document.GenreDoc;

import java.util.UUID;

public interface GenreCache {

    GenreDoc get(UUID uuid);

    void put(UUID uuid, GenreDoc genre);
}
