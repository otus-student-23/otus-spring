package ru.otus.mar.booklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.mapper.GenreMapper;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"mongock.enabled=false"})
public class GenreControllerTest {

    private static final Genre GENRE =
            new Genre("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "genre_a");

    private static final GenreDto GENRE_DTO =
            new GenreDto("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "genre_a");

    @MockBean
    private GenreRepository repo;

    @Autowired
    private GenreMapper mapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void list() {
        when(repo.findAll()).thenReturn(Flux.just(GENRE));

        webTestClient.get()
                .uri("/api/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class).contains(GENRE_DTO);
    }

    @Test
    public void get() {
        when(repo.findById(GENRE.getId())).thenReturn(Mono.just(GENRE));

        webTestClient.get()
                .uri("/api/genre/" + GENRE.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDto.class).isEqualTo(GENRE_DTO);
    }

    @Test
    public void create() {
        when(repo.save(any(Genre.class))).thenReturn(Mono.just(GENRE));

        Flux<GenreDto> result = webTestClient.post()
                .uri("/api/genre")
                .bodyValue(GENRE_DTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(GenreDto.class)
                .getResponseBody();
        assertThat(result.blockLast()).isEqualTo(GENRE_DTO);
    }

    @Test
    public void update() {
        when(repo.save(GENRE)).thenReturn(Mono.just(GENRE));

        Flux<GenreDto> result = webTestClient.put()
                .uri("/api/genre/" + GENRE.getId())
                .bodyValue(GENRE_DTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(GenreDto.class)
                .getResponseBody();
        assertThat(result.blockLast()).isEqualTo(GENRE_DTO);
    }

    @Test
    public void delete() {
        when(repo.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/genre/" + GENRE.getId())
                .exchange()
                .expectStatus().isOk().expectBody().isEmpty();
        verify(repo, times(1)).deleteById(GENRE.getId());
    }
}
