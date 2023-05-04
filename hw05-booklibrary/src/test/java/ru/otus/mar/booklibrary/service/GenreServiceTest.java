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

    private static final GenreDto GENRE_DTO = new GenreDto("Aaa Aaa Aaa");

    private static final Genre GENRE = new Genre(1L, "Aaa Aaa Aaa");

    @MockBean
    private GenreDao dao;

    @Autowired
    private GenreService service;

    @Test
    void createTest() {
        service.create(GENRE_DTO);
        verify(dao, times(1)).create(new Genre(GENRE.name()));
    }

    @Test
    void deleteTest() {
        when(dao.getByName(GENRE.name())).thenReturn(Optional.of(GENRE));
        service.delete(GENRE_DTO);
        verify(dao, times(1)).deleteById(1L);
    }

    @Test
    void getAllTest() {
        when(dao.getAll()).thenReturn(List.of(GENRE));
        assertTrue(service.getAll().containsAll(List.of(GENRE_DTO)));
    }

    @Test
    void getByNameTest() {
        when(dao.getByName(GENRE.name())).thenReturn(Optional.of(GENRE));
        assertEquals(GENRE_DTO, service.getByName(GENRE.name()).get());
    }

    @Test
    void getByFilterTest() {
        when(dao.getByFilter("test")).thenReturn(List.of(GENRE));
        assertTrue(service.getByFilter("test").containsAll(List.of(GENRE_DTO)));
    }
}
