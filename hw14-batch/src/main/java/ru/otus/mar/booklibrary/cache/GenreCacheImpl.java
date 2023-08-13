package ru.otus.mar.booklibrary.cache;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.document.GenreDoc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GenreCacheImpl implements GenreCache {

    private final Map<UUID, GenreDoc> genre = new HashMap<>();

    @Override
    public GenreDoc get(UUID uuid) {
        return genre.get(uuid);
    }

    @Override
    public void put(UUID uuid, GenreDoc author) {
        genre.put(uuid, author);
    }
}
