package ru.otus.mar.booklibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookCommentDaoJpa.class)
@Sql({"classpath:genre.sql", "classpath:author.sql", "classpath:book.sql"})
public class BookCommentDaoTest {

    private static final Book BOOK_A =
            new Book(
                    UUID.fromString("7f123ddc-3149-49e9-8ad1-b96837161e6c"),
                    "Book_A",
                    new Author(UUID.fromString("eb005816-caef-42b3-b096-f8c1cb16aef8"), "Author_A"),
                    new Genre(UUID.fromString("ef14f9b4-2e8b-44e8-a075-bb8c64cf7e3d"), "Genre_A"));

    @Autowired
    private BookCommentDao dao;

    @Test
    void insert() {
        dao.insert(new BookComment(BOOK_A, "comment_1"));
        dao.insert(new BookComment(BOOK_A, "comment_2"));
        assertEquals(2, dao.getByBook(BOOK_A).stream().count());
    }

    @Test
    void update() {
        BookComment comment = dao.insert(new BookComment(BOOK_A, "comment_1"));
        comment.setComment("comment_2");
        dao.update(comment);
        assertEquals("comment_2", dao.getById(comment.getId()).getComment());
    }

    @Test
    void delete() {
        dao.insert(new BookComment(BOOK_A, "comment_1"));
        BookComment comment = dao.insert(new BookComment(BOOK_A, "comment_2"));
        dao.delete(comment);
        assertEquals(1, dao.getByBook(BOOK_A).stream().count());
    }
}
