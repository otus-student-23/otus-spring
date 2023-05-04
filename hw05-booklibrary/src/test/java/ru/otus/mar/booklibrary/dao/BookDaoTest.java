package ru.otus.mar.booklibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"classpath:genre.sql", "classpath:author.sql", "classpath:book.sql"})
@Sql(scripts = "classpath:clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookDaoTest {

    @Autowired
    BookDao dao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void create() {
        Book book = new Book("Aaa Aaa Aaa", new Author(1L, "Aaaa Bbb Cc"), new Genre(1L, "Aaaa Bbb Cc"));
        assertEquals(book.getName(), dao.getById(dao.create(book).getId()).get().getName());
    }

    @Test
    void update() {
        Book book = dao.getByNameAndAuthor("Aaaa Bbb Cc", authorDao.getByName("Aaaa Bbb Cc").get()).get();
        book.setName("Aaa Aaa Aaa");
        dao.update(book);
        assertEquals(book, dao.getById(book.getId()).get());
    }

    @Test
    void deleteByIdTest() {
        dao.deleteById(dao.getByNameAndAuthor("Aaaa Bbb Cc", authorDao.getByName("Aaaa Bbb Cc").get()).get().getId());
        assertFalse(dao.getByNameAndAuthor("Aaaa Bbb Cc", authorDao.getByName("Aaaa Bbb Cc").get()).isPresent());
        assertTrue(dao.getByNameAndAuthor("BbbB Ccc Dd", authorDao.getByName("BbbB Ccc Dd").get()).isPresent());
    }

    @Test
    void getAllTest() {
        assertTrue(dao.getAll().containsAll(List.of(
                dao.getByNameAndAuthor("Aaaa Bbb Cc", authorDao.getByName("Aaaa Bbb Cc").get()).get(),
                dao.getByNameAndAuthor("BbbB Ccc Dd", authorDao.getByName("BbbB Ccc Dd").get()).get(),
                dao.getByNameAndAuthor("Cccc Ddd Ff", authorDao.getByName("Aaaa Bbb Cc").get()).get()
        )));
    }

    @Test
    void getByFilterTest() {
        assertThat(dao.getByFilter("substringof(tolower(name), 'bbb') and substringof(tolower(author), 'bbb') ")).containsExactly(
                dao.getByNameAndAuthor("Aaaa Bbb Cc", authorDao.getByName("Aaaa Bbb Cc").get()).get(),
                dao.getByNameAndAuthor("BbbB Ccc Dd", authorDao.getByName("BbbB Ccc Dd").get()).get()
        );
    }
}
