package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dao.AuthorDao;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.model.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Aaa Aaa Aaa");

    private static final Author AUTHOR = new Author(1L, "Aaa Aaa Aaa");

    @MockBean
    private AuthorDao dao;

    @Autowired
    private AuthorService service;

    @Test
    void createTest() {
        service.create(AUTHOR_DTO);
        verify(dao, times(1)).create(new Author(AUTHOR.name()));
    }

    @Test
    void deleteTest() {
        when(dao.getByName(AUTHOR.name())).thenReturn(Optional.of(AUTHOR));
        service.delete(AUTHOR_DTO);
        verify(dao, times(1)).deleteById(AUTHOR.id());
    }

    @Test
    void getAllTest() {
        when(dao.getAll()).thenReturn(List.of(AUTHOR));
        assertTrue(service.getAll().containsAll(List.of(AUTHOR_DTO)));
    }

    @Test
    void getByNameTest() {
        when(dao.getByName(AUTHOR.name())).thenReturn(Optional.of(AUTHOR));
        assertEquals(AUTHOR_DTO, service.getByName(AUTHOR.name()).get());
    }

    @Test
    void getByFilterTest() {
        when(dao.getByFilter("test")).thenReturn(List.of(AUTHOR));
        assertTrue(service.getByFilter("test").containsAll(List.of(AUTHOR_DTO)));
    }
}
