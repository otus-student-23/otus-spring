package ru.otus.mar.booklibrary.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreShellTest {

    @InjectMocks
    private GenreShell shell;

    @Mock
    private GenreService service;

    @Mock
    private EntityProvider prompt;

    private static final GenreDto GENRE_DTO = new GenreDto("Genre_A");

    @Test
    void add() {
        when(service.create(GENRE_DTO)).thenReturn(GENRE_DTO);
        assertEquals(GENRE_DTO, shell.add(GENRE_DTO.name()));
    }

    @Test
    void delete() {
        when(service.getByName(GENRE_DTO.name())).thenReturn(Optional.of(GENRE_DTO));
        when(prompt.getSelectedEntity()).thenReturn(GENRE_DTO);
        shell.get(GENRE_DTO.name());
        shell.delete();
        verify(service, times(1)).delete(GENRE_DTO);
    }

    @Test
    void getAll() {
        when(service.getAll()).thenReturn(List.of(GENRE_DTO));
        assertTrue(shell.list().containsAll(List.of(GENRE_DTO.name())));
    }

    @Test
    void getByName() {
        when(service.getByName(GENRE_DTO.name())).thenReturn(Optional.of(GENRE_DTO));
        assertEquals(GENRE_DTO, shell.get(GENRE_DTO.name()));
    }
}
