package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.cache.GenreCache;
import ru.otus.mar.booklibrary.model.document.GenreDoc;
import ru.otus.mar.booklibrary.model.entity.Genre;

@RequiredArgsConstructor
@Service

public class GenreServiceImpl implements GenreService {

    private final GenreCache cache;

    @Override
    public GenreDoc buildDocument(Genre genre) {
        GenreDoc genreDoc = new GenreDoc(ObjectId.get(), genre.getName());
        cache.put(genre.getId(), genreDoc);
        return genreDoc;
    }
}
