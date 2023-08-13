package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.cache.BookCache;
import ru.otus.mar.booklibrary.cache.GenreCache;
import ru.otus.mar.booklibrary.model.document.AuthorDoc;
import ru.otus.mar.booklibrary.model.document.BookDoc;
import ru.otus.mar.booklibrary.model.entity.Book;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final MongoTemplate mongoTemplate;

    private final BookCache bookCache;

    private final GenreCache genreCache;

    @Override
    public BookDoc buildDocument(Book book) {
        ObjectId objectId = ObjectId.get();
        bookCache.put(book.getId(), objectId);
        return new BookDoc(
                objectId,
                book.getName(),
                mongoTemplate.findOne(query(where("name").is(book.getAuthor().getName())), AuthorDoc.class),
                genreCache.get(book.getGenre().getId())
        );
    }
}
