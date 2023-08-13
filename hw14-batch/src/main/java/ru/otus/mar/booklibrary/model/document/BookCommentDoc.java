package ru.otus.mar.booklibrary.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book_comment")
public class BookCommentDoc {

    @Id
    private ObjectId id;

    @DBRef
    @Indexed
    private BookDoc book;

    private String comment;
}
