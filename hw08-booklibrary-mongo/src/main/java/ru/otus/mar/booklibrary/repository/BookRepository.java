package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, QuerydslPredicateExecutor<Book> {

    Optional<Book> findByNameAndAuthor(String name, Author author);

    boolean existsByAuthor(Author author);

    boolean existsByGenre(Genre genre);
}
