package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.mar.booklibrary.model.Author;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @RestResource(path = "names", rel = "names")
    Optional<Author> findByName(String name);
}
