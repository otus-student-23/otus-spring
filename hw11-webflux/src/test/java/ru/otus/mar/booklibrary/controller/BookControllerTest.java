package ru.otus.mar.booklibrary.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"mongock.enabled=false"})

public class BookControllerTest {

    private static final Book BOOK = new Book(
            "301c28f7-1793-45dd-91a1-8c0ec82d5beb",
            "book_a",
            new Author("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "author_a"),
            new Genre("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "genre_a"));

    private static final BookDto BOOK_DTO = new BookDto(
            "301c28f7-1793-45dd-91a1-8c0ec82d5beb",
            "book_a",
            new AuthorDto("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "author_a"),
            new GenreDto("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "genre_a"));

    @MockBean
    private BookRepository bookRepo;

    @MockBean
    private AuthorRepository authorRepo;

    @MockBean
    private GenreRepository genreRepo;

    @Autowired
    private BookMapper mapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void list() throws Exception {
        when(bookRepo.findAll()).thenReturn(Flux.just(BOOK));

        webTestClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class).contains(BOOK_DTO);
    }

    @Test
    public void get() {
        when(bookRepo.findById(BOOK.getId())).thenReturn(Mono.just(BOOK));

        webTestClient.get()
                .uri("/api/book/" + BOOK.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class).isEqualTo(BOOK_DTO);
    }

    @Test
    public void create() {
        when(authorRepo.findByName(anyString())).thenReturn(Mono.just(BOOK.getAuthor()));
        when(authorRepo.save(any(Author.class))).thenReturn(Mono.just(BOOK.getAuthor()));
        when(genreRepo.findByName(anyString())).thenReturn(Mono.just(BOOK.getGenre()));
        when(genreRepo.save(any(Genre.class))).thenReturn(Mono.just(BOOK.getGenre()));
        when(bookRepo.save(any(Book.class))).thenReturn(Mono.just(BOOK));

        Flux<BookDto> result = webTestClient.post()
                .uri("/api/book")
                .bodyValue(BOOK_DTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();
        assertThat(result.blockLast()).isEqualTo(BOOK_DTO);
    }

    @Test
    public void update() {
        when(authorRepo.findByName(anyString())).thenReturn(Mono.just(BOOK.getAuthor()));
        when(authorRepo.save(any(Author.class))).thenReturn(Mono.just(BOOK.getAuthor()));
        when(genreRepo.findByName(anyString())).thenReturn(Mono.just(BOOK.getGenre()));
        when(genreRepo.save(any(Genre.class))).thenReturn(Mono.just(BOOK.getGenre()));
        when(bookRepo.save(BOOK)).thenReturn(Mono.just(BOOK));

        Flux<BookDto> result = webTestClient.put()
                .uri("/api/book/" + BOOK.getId())
                .bodyValue(BOOK_DTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();
        assertThat(result.blockLast()).isEqualTo(BOOK_DTO);
    }

    @Test
    public void delete() {
        when(bookRepo.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/" + BOOK.getId())
                .exchange()
                .expectStatus().isOk().expectBody().isEmpty();
        verify(bookRepo, times(1)).deleteById(BOOK.getId());
    }
}
