package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findByName(String name);

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findByNameAndAuthor(String name, Author author);

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
