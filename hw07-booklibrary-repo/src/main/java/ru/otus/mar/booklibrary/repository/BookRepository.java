package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByName(String name);

    Optional<Book> findByNameAndAuthor(String name, Author author);//TODO n+1
}
