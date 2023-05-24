package ru.otus.mar.booklibrary.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import java.util.stream.Stream;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "student", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "student")
    public void insertAuthors(AuthorRepository repo) {
        Stream.iterate('a', i -> (char) (i + 1)).limit(5).forEach(
                c -> repo.save(new Author("author_" + c))
        );
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "student")
    public void insertGenres(GenreRepository repo) {
        Stream.iterate('a', i -> (char) (i + 1)).limit(5).forEach(
                c -> repo.save(new Genre("genre_" + c))
        );
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "student")
    public void insertBooks(BookRepository repo, AuthorRepository authorRepo, GenreRepository genreRepo) {
        repo.save(new Book("book_a", authorRepo.findByName("author_a").get(), genreRepo.findByName("genre_a").get()));
        repo.save(new Book("book_b", authorRepo.findByName("author_b").get(), genreRepo.findByName("genre_b").get()));
        repo.save(new Book("book_c", authorRepo.findByName("author_b").get(), genreRepo.findByName("genre_c").get()));
        repo.save(new Book("book_d", authorRepo.findByName("author_c").get(), genreRepo.findByName("genre_b").get()));
        repo.save(new Book("book_e", authorRepo.findByName("author_c").get(), genreRepo.findByName("genre_c").get()));
    }
}
