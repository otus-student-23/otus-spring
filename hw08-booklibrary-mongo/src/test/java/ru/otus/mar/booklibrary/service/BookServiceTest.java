package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static final Book BOOK = new Book("Book_1", new Author("Author_1"), new Genre("Genre_1"));

    private static final BookDto BOOK_DTO = new BookDto("Book_1", new AuthorDto("Author_1"), new GenreDto("Genre_1"));

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepo;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookCommentRepository bookCommentRepo;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepo;

    @Mock
    private GenreService genreService;

    @Test
    void create() {
        when(authorService.create(BOOK_DTO.getAuthor())).thenReturn(BOOK_DTO.getAuthor());
        when(genreService.create(BOOK_DTO.getGenre())).thenReturn(BOOK_DTO.getGenre());
        when(bookRepo.save(any())).thenReturn(BOOK);
        when(bookMapper.toDto(BOOK)).thenReturn(BOOK_DTO);
        assertEquals(BOOK_DTO, bookService.create(BOOK_DTO));
    }

    @Test
    void update() {
        when(authorService.create(BOOK_DTO.getAuthor())).thenReturn(BOOK_DTO.getAuthor());
        when(genreService.create(BOOK_DTO.getGenre())).thenReturn(BOOK_DTO.getGenre());
        when(bookRepo.save(any())).thenReturn(BOOK);
        when(bookMapper.toDto(BOOK)).thenReturn(BOOK_DTO);
        when(bookMapper.fromDto(BOOK_DTO)).thenReturn(BOOK);
        assertEquals(BOOK_DTO, bookService.update(BOOK_DTO));
    }

    @Test
    void delete() {
        bookService.delete(BOOK_DTO);
        verify(bookRepo, times(1)).deleteById(BOOK.getId());
    }

    @Test
    void getAll() {
        when(bookRepo.findAll()).thenReturn(List.of(BOOK));
        when(bookMapper.toDto(BOOK)).thenReturn(BOOK_DTO);
        assertTrue(bookService.getAll().containsAll(List.of(BOOK_DTO)));
    }
}
