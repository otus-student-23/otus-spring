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
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.repository.AuthorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"mongock.enabled=false"})
public class AuthorControllerTest {

    private static final Author AUTHOR =
            new Author("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "author_a");

    private static final AuthorDto AUTHOR_DTO =
            new AuthorDto("301c28f7-1793-45dd-91a1-8c0ec82d5beb", "author_a");

    @MockBean
    private AuthorRepository repo;

    @Autowired
    private AuthorMapper mapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void list() {
        when(repo.findAll()).thenReturn(Flux.just(AUTHOR));

        webTestClient.get()
                .uri("/api/author")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class).contains(AUTHOR_DTO);
    }

    @Test
    public void get() {
        when(repo.findById(AUTHOR.getId())).thenReturn(Mono.just(AUTHOR));

        webTestClient.get()
                .uri("/api/author/" + AUTHOR.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDto.class).isEqualTo(AUTHOR_DTO);
    }

    @Test
    public void create() {
        when(repo.save(any(Author.class))).thenReturn(Mono.just(AUTHOR));

        Flux<AuthorDto> result = webTestClient.post()
                .uri("/api/author")
                .bodyValue(AUTHOR_DTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody();
        assertThat(result.blockLast()).isEqualTo(AUTHOR_DTO);
    }

    @Test
    public void update() {
        when(repo.save(AUTHOR)).thenReturn(Mono.just(AUTHOR));

        Flux<AuthorDto> result = webTestClient.put()
                .uri("/api/author/" + AUTHOR.getId())
                .bodyValue(AUTHOR_DTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody();
        assertThat(result.blockLast()).isEqualTo(AUTHOR_DTO);
    }

    @Test
    public void delete() {
        when(repo.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/author/" + AUTHOR.getId())
                .exchange()
                .expectStatus().isOk().expectBody().isEmpty();
        verify(repo, times(1)).deleteById(AUTHOR.getId());
    }
}
