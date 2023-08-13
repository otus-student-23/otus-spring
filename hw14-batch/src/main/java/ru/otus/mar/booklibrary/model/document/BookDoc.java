package ru.otus.mar.booklibrary.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
@CompoundIndexes({
        @CompoundIndex(name = "name_author_idx", def = "{'name': 1, 'author': 1}", unique = true)
})
public class BookDoc {

    @Id
    private ObjectId id;

    private String name;

    private AuthorDoc author;

    private GenreDoc genre;
}
