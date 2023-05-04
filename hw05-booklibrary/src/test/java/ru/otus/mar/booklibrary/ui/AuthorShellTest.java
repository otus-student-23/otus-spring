package ru.otus.mar.booklibrary.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorShellTest {

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Aaa Aaa Aaa");

    @MockBean
    private AuthorService service;

    @Autowired
    private AuthorShell shell;

    @Test
    void addTest() {
        shell.add(AUTHOR_DTO.name());
        verify(service, times(1)).create(AUTHOR_DTO);
    }

    @Test
    void deleteTest() {
        when(service.getByName(AUTHOR_DTO.name())).thenReturn(Optional.of(AUTHOR_DTO));
        shell.delete(AUTHOR_DTO.name());
        verify(service, times(1)).delete(AUTHOR_DTO);
    }

    @Test
    void getAllTest() {
        when(service.getAll()).thenReturn(List.of(AUTHOR_DTO));
        assertTrue(shell.list(null).containsAll(List.of(AUTHOR_DTO)));
    }

    @Test
    void getByNameTest() {
        when(service.getByName(AUTHOR_DTO.name())).thenReturn(Optional.of(AUTHOR_DTO));
        assertEquals(AUTHOR_DTO, shell.get(AUTHOR_DTO.name()));
    }

    @Test
    void getByFilterTest() {
        when(service.getByFilter("test")).thenReturn(List.of(AUTHOR_DTO));
        assertTrue(shell.list("test").containsAll(List.of(AUTHOR_DTO)));
    }
}
