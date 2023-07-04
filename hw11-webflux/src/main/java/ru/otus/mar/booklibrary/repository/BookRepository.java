package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.mar.booklibrary.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Void> deleteByAuthorId(String authorId);

    Mono<Void> deleteByGenreId(String genreId);
}
