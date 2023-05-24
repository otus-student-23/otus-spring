package ru.otus.mar.booklibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {

    @Id
    private String id;

    @Indexed
    private String name;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this(null, name, author, genre);
    }
}
