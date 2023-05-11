package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dao.GenreDao;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GenreServiceTest {

    private static final GenreDto GENRE_DTO = new GenreDto("Genre_1");

    private static final Genre GENRE = new Genre("Genre_1");

    @Autowired
    private GenreService service;

    @MockBean
    private GenreDao dao;

    @Test
    void create() {
        when(dao.getByName(GENRE.getName())).thenReturn(Optional.of(GENRE));
        assertEquals(GENRE_DTO, service.create(GENRE_DTO));
    }

    @Test
    void update() {
        when(dao.update(any())).thenReturn(GENRE);
        assertEquals(GENRE_DTO, service.update(GENRE_DTO));
    }

    @Test
    void delete() {
        when(dao.getByName(GENRE.getName())).thenReturn(Optional.of(GENRE));
        service.delete(GENRE_DTO);
        verify(dao, times(1)).delete(GENRE);
    }

    @Test
    void getAll() {
        when(dao.getAll()).thenReturn(List.of(GENRE));
        assertTrue(service.getAll().containsAll(List.of(GENRE_DTO)));
    }

    @Test
    void getByName() {
        when(dao.getByName(GENRE.getName())).thenReturn(Optional.of(GENRE));
        assertEquals(GENRE_DTO, service.getByName(GENRE.getName()).get());
    }
}
