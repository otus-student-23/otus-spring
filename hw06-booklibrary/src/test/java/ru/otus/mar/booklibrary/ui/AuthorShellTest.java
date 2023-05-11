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

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Author_A");

    @MockBean
    private AuthorService service;

    @Autowired
    private AuthorShell shell;

    @Test
    void add() {
        when(service.create(AUTHOR_DTO)).thenReturn(AUTHOR_DTO);
        assertEquals(AUTHOR_DTO, shell.add(AUTHOR_DTO.name()));
    }

    @Test
    void delete() {
        when(service.getByName(AUTHOR_DTO.name())).thenReturn(Optional.of(AUTHOR_DTO));
        shell.get(AUTHOR_DTO.name());
        shell.delete();
        verify(service, times(1)).delete(AUTHOR_DTO);
    }

    @Test
    void getAll() {
        when(service.getAll()).thenReturn(List.of(AUTHOR_DTO));
        assertTrue(shell.list().containsAll(List.of(AUTHOR_DTO.name())));
    }

    @Test
    void getByName() {
        when(service.getByName(AUTHOR_DTO.name())).thenReturn(Optional.of(AUTHOR_DTO));
        assertEquals(AUTHOR_DTO, shell.get(AUTHOR_DTO.name()));
    }
}
