package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.mar.booklibrary.model.BookComment;

public interface BookCommentRepository extends ReactiveMongoRepository<BookComment, String> {

    Flux<BookComment> findByBookId(String bookId);
}
