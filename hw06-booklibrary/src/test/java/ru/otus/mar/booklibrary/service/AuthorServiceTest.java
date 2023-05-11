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

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Author_1");

    private static final Author AUTHOR = new Author("Author_1");

    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorDao dao;

    @Test
    void create() {
        when(dao.getByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        assertEquals(AUTHOR_DTO, service.create(AUTHOR_DTO));
    }

    @Test
    void update() {
        when(dao.update(any())).thenReturn(AUTHOR);
        assertEquals(AUTHOR_DTO, service.update(AUTHOR_DTO));
    }

    @Test
    void delete() {
        when(dao.getByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        service.delete(AUTHOR_DTO);
        verify(dao, times(1)).delete(AUTHOR);
    }

    @Test
    void getAll() {
        when(dao.getAll()).thenReturn(List.of(AUTHOR));
        assertTrue(service.getAll().containsAll(List.of(AUTHOR_DTO)));
    }

    @Test
    void getByName() {
        when(dao.getByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        assertEquals(AUTHOR_DTO, service.getByName(AUTHOR.getName()).get());
    }
}
