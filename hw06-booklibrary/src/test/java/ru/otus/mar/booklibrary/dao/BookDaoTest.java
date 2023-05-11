package ru.otus.mar.booklibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookDaoJpa.class)
@Sql({"classpath:genre.sql", "classpath:author.sql", "classpath:book.sql"})
public class BookDaoTest {

    private static final Author AUTHOR_A = new Author(UUID.fromString("eb005816-caef-42b3-b096-f8c1cb16aef8"), "Author_A");

    private static final Genre GENRE_A = new Genre(UUID.fromString("ef14f9b4-2e8b-44e8-a075-bb8c64cf7e3d"), "Genre_A");

    @Autowired
    private BookDao dao;

    @Test
    void insert() {
        Book book = new Book("Book_1", AUTHOR_A, GENRE_A);
        dao.insert(book);
        assertEquals(book, dao.getById(book.getId()));
    }

    @Test
    void update() {
        Book book = dao.getByNameAndAuthor("Book_A", AUTHOR_A).get();
        book.setName("Book_1");
        dao.update(book);
        assertEquals(book, dao.getById(book.getId()));
    }

    @Test
    void delete() {
        dao.delete(dao.getByNameAndAuthor("Book_A", AUTHOR_A).get());
        assertFalse(dao.getByNameAndAuthor("Book_A", AUTHOR_A).isPresent());
        assertTrue(dao.getByNameAndAuthor("Book_B", AUTHOR_A).isPresent());
    }

    @Test
    void getAll() {
        assertTrue(dao.getAll().containsAll(List.of(
                dao.getByNameAndAuthor("Book_A", AUTHOR_A).get(),
                dao.getByNameAndAuthor("Book_B", AUTHOR_A).get(),
                dao.getByNameAndAuthor("Book_C", new Author(UUID.fromString("a4f96b65-d197-4040-aaaf-ee999e3098ee"), "Author_B")).get()
        )));
    }
}
