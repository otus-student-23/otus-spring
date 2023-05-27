package ru.otus.mar.booklibrary.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorShellTest {

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Author_A");

    @InjectMocks
    private AuthorShell shell;

    @Mock
    private AuthorService service;

    @Mock
    private EntityProvider prompt;

    @Test
    void add() {
        when(service.create(AUTHOR_DTO)).thenReturn(AUTHOR_DTO);
        assertEquals(AUTHOR_DTO, shell.add(AUTHOR_DTO.name()));
    }

    @Test
    void delete() {
        when(service.getByName(AUTHOR_DTO.name())).thenReturn(Optional.of(AUTHOR_DTO));
        when(prompt.getSelectedEntity()).thenReturn(AUTHOR_DTO);
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
