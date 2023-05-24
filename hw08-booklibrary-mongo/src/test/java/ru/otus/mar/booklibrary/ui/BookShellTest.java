package ru.otus.mar.booklibrary.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.AuthorService;
import ru.otus.mar.booklibrary.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookShellTest {

    private static final BookDto BOOK_DTO = new BookDto(
            null,
            "Book_1",
            new AuthorDto("Author_1"),
            new GenreDto("Genre_1"));

    @InjectMocks
    private BookShell shell;

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @Mock
    private EntityProvider prompt;

    @Test
    void add() {
        shell.add(BOOK_DTO.getName(), BOOK_DTO.getAuthor().name(), BOOK_DTO.getGenre().name());
        verify(bookService, times(1)).create(BOOK_DTO);
    }

    @Test
    void delete() {
        when(bookService.getByNameAndAuthor(BOOK_DTO.getName(), BOOK_DTO.getAuthor())).thenReturn(Optional.of(BOOK_DTO));
        when(authorService.getByName(anyString())).thenReturn(Optional.ofNullable(BOOK_DTO.getAuthor()));
        when(prompt.getSelectedEntity()).thenReturn(BOOK_DTO);
        shell.get(BOOK_DTO.getName(), BOOK_DTO.getAuthor().name());
        shell.delete();
        verify(bookService, times(1)).delete(BOOK_DTO);
    }

    @Test
    void getAll() {
        when(bookService.getAll()).thenReturn(List.of(BOOK_DTO));
        assertTrue(shell.list(null, null, null)
                .containsAll(List.of(List.of(BOOK_DTO.getName(), BOOK_DTO.getAuthor().name(), BOOK_DTO.getGenre().name()))));
    }

    @Test
    void getByName() {
        when(bookService.getByNameAndAuthor(BOOK_DTO.getName(), BOOK_DTO.getAuthor())).thenReturn(Optional.of(BOOK_DTO));
        when(authorService.getByName(anyString())).thenReturn(Optional.ofNullable(BOOK_DTO.getAuthor()));
        assertEquals(BOOK_DTO, shell.get(BOOK_DTO.getName(), BOOK_DTO.getAuthor().name()));
    }
}
