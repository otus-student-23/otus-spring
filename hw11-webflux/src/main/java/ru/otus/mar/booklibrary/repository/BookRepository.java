package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.mar.booklibrary.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
