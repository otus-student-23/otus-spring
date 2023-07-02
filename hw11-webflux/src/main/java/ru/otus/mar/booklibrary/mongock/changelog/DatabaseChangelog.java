package ru.otus.mar.booklibrary.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
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

    @ChangeSet(order = "003", id = "insertAuthors", author = "student")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> collection = db.getCollection("author");
        Stream.iterate('a', i -> (char) (i + 1)).limit(5).forEach(
                c -> collection.insertOne(new Document("name", "author_" + c))
        );
        collection.createIndex(new Document("name", 1), new IndexOptions().name("name_idx").unique(true));

        //Bson command = new BsonDocument("dbStats", new BsonInt64(1));
        //Bson command = new BsonDocument("replSetInitiate", new BsonNull());
        //Document commandResult = db.runCommand(command);
        //System.out.println(commandResult.toJson());
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
        addBook(new Book("book_a", new Author("author_a"), new Genre("genre_a")), repo, authorRepo, genreRepo);
        addBook(new Book("book_b", new Author("author_b"), new Genre("genre_b")), repo, authorRepo, genreRepo);
        addBook(new Book("book_c", new Author("author_b"), new Genre("genre_c")), repo, authorRepo, genreRepo);
        addBook(new Book("book_d", new Author("author_c"), new Genre("genre_b")), repo, authorRepo, genreRepo);
        addBook(new Book("book_e", new Author("author_c"), new Genre("genre_c")), repo, authorRepo, genreRepo);

        MongoCollection<Document> collection = db.getCollection("book");
        collection.createIndex(new Document("name", 1).append("author", 1),
                new IndexOptions().name("name_author_idx").unique(true));
    }

    private void addBook(Book book, BookRepository repo, AuthorRepository authorRepo, GenreRepository genreRepo) {
        authorRepo.findByName(book.getAuthor().getName())
                .switchIfEmpty(authorRepo.insert(book.getAuthor()))
                .doOnNext(book::setAuthor)
                .flatMap(a -> genreRepo.findByName(book.getGenre().getName()))
                .switchIfEmpty(genreRepo.insert(book.getGenre()))
                .doOnNext(book::setGenre)
                .flatMap(g -> repo.save(book))
                .block();
    }
}
