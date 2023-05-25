package ru.otus.mar.booklibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
@CompoundIndexes({
        @CompoundIndex(name = "name_author_idx", def = "{'name': 1, 'author': 1}", unique = true)
})
public class Book {

    @Id
    private String id;

    private String name;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this(null, name, author, genre);
    }
}
