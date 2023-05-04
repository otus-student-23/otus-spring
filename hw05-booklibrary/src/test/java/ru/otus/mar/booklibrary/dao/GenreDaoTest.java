package ru.otus.mar.booklibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:genre.sql")
@Sql(scripts = "classpath:clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GenreDaoTest {

    @Autowired
    GenreDao dao;

    @Test
    void create() {
        Genre genre = new Genre("Aaa Aaa Aaa");
        assertEquals(genre.name(), dao.getById(dao.create(genre).id()).get().name());
    }

    @Test
    void update() {
        Genre genre = new Genre(dao.getByName("Aaaa Bbb Cc").get().id(), "Aaa Aaa Aaa");
        dao.update(genre);
        assertEquals(genre, dao.getById(genre.id()).get());
    }

    @Test
    void deleteByIdTest() {
        dao.deleteById(dao.getByName("Aaaa Bbb Cc").get().id());
        assertFalse(dao.getByName("Aaaa Bbb Cc").isPresent());
        assertTrue(dao.getByName("BbbB Ccc Dd").isPresent());
    }

    @Test
    void getAllTest() {
        assertTrue(dao.getAll().containsAll(List.of(
                dao.getByName("Aaaa Bbb Cc").get(),
                dao.getByName("BbbB Ccc Dd").get(),
                dao.getByName("Cccc Ddd Ff").get()
        )));
    }

    @Test
    void getByFilterTest() {
        assertThat(dao.getByFilter("substringof(tolower(name), 'bbb')")).containsExactly(
                dao.getByName("Aaaa Bbb Cc").get(),
                dao.getByName("BbbB Ccc Dd").get()
        );
    }
}
