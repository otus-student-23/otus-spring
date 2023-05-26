package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mar.booklibrary.model.BookComment;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {

    List<BookComment> findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
