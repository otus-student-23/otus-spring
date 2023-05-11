package ru.otus.mar.booklibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.mar.booklibrary.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorDaoJpa.class)
@Sql("classpath:author.sql")
public class AuthorDaoTest {

    @Autowired
    private AuthorDao dao;

    @Test
    void insert() {
        Author author = new Author("Author_1");
        dao.insert(author);
        assertEquals("Author_1", dao.getById(author.getId()).getName());
    }

    @Test
    void update() {
        Author author = dao.getByName("Author_A").get();
        author.setName("Author_1");
        dao.update(author);
        assertEquals("Author_1", dao.getById(author.getId()).getName());
    }

    @Test
    void delete() {
        dao.delete(dao.getByName("Author_A").get());
        assertFalse(dao.getByName("Author_A").isPresent());
        assertTrue(dao.getByName("Author_B").isPresent());
    }

    @Test
    void getAll() {
        assertTrue(dao.getAll().stream().map(Author::getName).toList()
                .containsAll(List.of("Author_A", "Author_B", "Author_C")));
    }
}
