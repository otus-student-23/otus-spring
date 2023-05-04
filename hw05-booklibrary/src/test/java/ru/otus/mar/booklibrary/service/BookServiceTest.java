package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dao.BookDao;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    private static final Book BOOK = new Book(1L, "Aaa Aaa Aaa", new Author(1L, "Aaa Aaa Aaa"), new Genre(1L, "Aaa Aaa Aaa"));

    private static final BookDto BOOK_DTO = new BookDto("Aaa Aaa Aaa", "Aaa Aaa Aaa", "Aaa Aaa Aaa");

    @MockBean
    private BookDao dao;

    @Autowired
    private BookService service;

    @Test
    void createTest() {
        service.create(BOOK_DTO);
        verify(dao, times(1)).create(new Book(BOOK_DTO.name(),
                new Author(1L, BOOK_DTO.author()), new Genre(1l, BOOK_DTO.genre())));
    }

    @Test
    void deleteTest() {
        when(dao.getByNameAndAuthor(BOOK.getName(), BOOK.getAuthor())).thenReturn(Optional.of(BOOK));
        service.delete(BOOK_DTO);
        verify(dao, times(1)).deleteById(BOOK.getId());
    }

    @Test
    void getAllTest() {
        when(dao.getAll()).thenReturn(List.of(BOOK));
        assertTrue(service.getAll().containsAll(List.of(BOOK_DTO)));
    }

    @Test
    void getByFilterTest() {
        when(dao.getByFilter("test")).thenReturn(List.of(BOOK));
        assertTrue(service.getByFilter("test").containsAll(List.of(BOOK_DTO)));
    }
}
