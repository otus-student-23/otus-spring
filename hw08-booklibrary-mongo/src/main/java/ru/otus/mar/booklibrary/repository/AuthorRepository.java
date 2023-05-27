package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mar.booklibrary.model.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByName(String name);
}
