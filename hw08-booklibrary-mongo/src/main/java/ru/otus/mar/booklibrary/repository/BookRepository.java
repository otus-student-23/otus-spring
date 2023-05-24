package ru.otus.mar.booklibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByNameAndAuthor(String name, Author author);

    Optional<Book> findFirstByAuthor(Author author);

    Optional<Book> findFirstByGenre(Genre genre);

    //TODO problem n+1 for findAll

    //@Override
    //List<Book> findAll(Specification<Book> filter);//TODO JpaSpecificationExecutor -> querydsl QueryDslPredicateExecutor
}
