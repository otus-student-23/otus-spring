package ru.otus.mar.booklibrary.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dao.GenreDao;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreShellTest {

    @MockBean
    private GenreService service;

    @MockBean
    private GenreDao dao;

    @Autowired
    private GenreShell shell;

    private final String genreName = "Aaa Aaa Aaa";

    @Test
    void addTest() {
        shell.add(genreName);
        verify(service, times(1)).create(new GenreDto(genreName));
    }

    @Test
    void deleteTest() {
        when(service.getByName(genreName)).thenReturn(Optional.of(new GenreDto(genreName)));
        shell.delete(genreName);
        verify(service, times(1)).delete(new GenreDto(genreName));
    }

    @Test
    void getAllTest() {
        when(service.getAll()).thenReturn(List.of(new GenreDto(genreName)));
        assertTrue(shell.list(null).containsAll(List.of(new GenreDto(genreName))));
    }

    @Test
    void getByNameTest() {
        when(service.getByName(genreName)).thenReturn(Optional.of(new GenreDto(genreName)));
        assertEquals(new GenreDto(genreName), shell.get(genreName));
    }

    @Test
    void getByFilterTest() {
        when(service.getByFilter("test")).thenReturn(List.of(new GenreDto(genreName)));
        assertTrue(shell.list("test").containsAll(List.of(new GenreDto(genreName))));
    }
}
