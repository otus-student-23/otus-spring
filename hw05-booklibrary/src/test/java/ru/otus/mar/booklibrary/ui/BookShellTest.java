package ru.otus.mar.booklibrary.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dao.BookDao;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookShellTest {

    private static final Book BOOK = new Book(1L, "Aaa Aaa Aaa", new Author(1L, "Aaa Aaa Aaa"), new Genre(1L, "Aaa Aaa Aaa"));

    private static final BookDto BOOK_DTO = new BookDto("Aaa Aaa Aaa", "Aaa Aaa Aaa", "Aaa Aaa Aaa");


    @MockBean
    private BookService service;

    @MockBean
    private BookDao dao;

    @Autowired
    private BookShell shell;

    @Test
    void addTest() {
        shell.add(BOOK_DTO.name(), BOOK_DTO.author(), BOOK_DTO.genre());
        verify(service, times(1)).create(BOOK_DTO);
    }

    @Test
    void deleteTest() {
        when(service.getByNameAndAuthor(BOOK_DTO.name(), BOOK_DTO.author())).thenReturn(Optional.of(BOOK_DTO));
        shell.delete(BOOK_DTO.name(), BOOK_DTO.author());
        verify(service, times(1)).delete(new BookDto(BOOK_DTO.name(), BOOK_DTO.author(), null));
    }

    @Test
    void getAllTest() {
        when(service.getAll()).thenReturn(List.of(BOOK_DTO));
        assertTrue(shell.list(null).containsAll(List.of(BOOK_DTO)));
    }

    @Test
    void getByNameTest() {
        when(service.getByNameAndAuthor(BOOK_DTO.name(), BOOK_DTO.author())).thenReturn(Optional.of(BOOK_DTO));
        assertEquals(BOOK_DTO, shell.get(BOOK_DTO.name(), BOOK_DTO.author()));
    }

    @Test
    void getByFilterTest() {
        when(service.getByFilter("test")).thenReturn(List.of(BOOK_DTO));
        assertTrue(shell.list("test").containsAll(List.of(BOOK_DTO)));
    }
}
