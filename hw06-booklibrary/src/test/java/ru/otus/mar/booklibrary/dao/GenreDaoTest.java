package ru.otus.mar.booklibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GenreDaoJpa.class)
@Sql("classpath:genre.sql")
public class GenreDaoTest {

    @Autowired
    private GenreDao dao;

    @Test
    void insert() {
        Genre genre = new Genre("Genre_1");
        dao.insert(genre);
        assertEquals("Genre_1", dao.getById(genre.getId()).getName());
    }

    @Test
    void update() {
        Genre genre = dao.getByName("Genre_A").get();
        genre.setName("Genre_1");
        dao.update(genre);
        assertEquals("Genre_1", dao.getById(genre.getId()).getName());
    }

    @Test
    void delete() {
        dao.delete(dao.getByName("Genre_A").get());
        assertFalse(dao.getByName("Genre_A").isPresent());
        assertTrue(dao.getByName("Genre_B").isPresent());
    }

    @Test
    void getAll() {
        assertTrue(dao.getAll().stream().map(Genre::getName).toList()
                .containsAll(List.of("Genre_A", "Genre_B", "Genre_C")));
    }
}
