package ru.otus.mar.booklibrary.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import ru.otus.mar.booklibrary.model.Book;
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

    @ChangeSet(order = "003", id = "insertAuthors", author = "student")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> collection = db.getCollection("author");
        Stream.iterate('a', i -> (char) (i + 1)).limit(5).forEach(
                c -> collection.insertOne(new Document("name", "author_" + c))
        );
        collection.createIndex(new Document("name", 1), new IndexOptions().name("name_idx").unique(true));
    }

    @ChangeSet(order = "004", id = "insertGenres", author = "student")
    public void insertGenres(MongoDatabase db) {
        MongoCollection<Document> collection = db.getCollection("genre");
        Stream.iterate('a', i -> (char) (i + 1)).limit(5).forEach(
                c -> collection.insertOne(new Document("name", "genre_" + c))
        );
        collection.createIndex(new Document("name", 1), new IndexOptions().name("name_idx").unique(true));
    }

    @ChangeSet(order = "005", id = "insertBooks", author = "student")
    public void insertBooks(MongoDatabase db, BookRepository repo,
                            AuthorRepository authorRepo, GenreRepository genreRepo) {
        repo.save(new Book("book_a", authorRepo.findByName("author_a").get(), genreRepo.findByName("genre_a").get()));
        repo.save(new Book("book_b", authorRepo.findByName("author_b").get(), genreRepo.findByName("genre_b").get()));
        repo.save(new Book("book_c", authorRepo.findByName("author_b").get(), genreRepo.findByName("genre_c").get()));
        repo.save(new Book("book_d", authorRepo.findByName("author_c").get(), genreRepo.findByName("genre_b").get()));
        repo.save(new Book("book_e", authorRepo.findByName("author_c").get(), genreRepo.findByName("genre_c").get()));

        MongoCollection<Document> collection = db.getCollection("book");
        collection.createIndex(new Document("name", 1).append("author", 1),
                new IndexOptions().name("name_author_idx").unique(true));
    }
}
