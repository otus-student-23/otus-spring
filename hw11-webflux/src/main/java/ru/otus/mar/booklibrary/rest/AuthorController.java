package ru.otus.mar.booklibrary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.BsonObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.SseDto;
import ru.otus.mar.booklibrary.exception.NotFoundException;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;

import static com.mongodb.client.model.changestream.OperationType.DELETE;

@RestController
@RequiredArgsConstructor
@Tag(name = "Авторы", description = "Авторы")
public class AuthorController {

    private final AuthorRepository repo;

    private final BookRepository bookRepo;

    private final AuthorMapper mapper;

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping("/api/author")
    @Operation(summary = "Полный список")
    public Flux<AuthorDto> list() {
        return repo.findAll().map(mapper::toDto);
    }

    @GetMapping("/api/author/stream")
    @Operation(summary = "Поток изменений")
    public Flux<SseDto<AuthorDto>> stream() {
        return mongoTemplate.changeStream(Author.class)
                .watchCollection("author")
                //.filter(where("operationType").in("insert", "replace", "update", "delete"))
                .listen()
                .map(e -> new SseDto<>(
                        e.getOperationType().name(),
                        e.getOperationType() == DELETE
                                ? new AuthorDto(((BsonObjectId) e.getRaw().getDocumentKey().get("_id"))
                                .getValue().toString(), null)
                                : mapper.toDto(e.getBody()))
                );
    }

    @GetMapping("/api/author/{id}")
    @Operation(summary = "Получить")
    public Mono<AuthorDto> get(@PathVariable String id) {
        return repo.findById(id).map(mapper::toDto)
                .switchIfEmpty(Mono.error(new NotFoundException()));
    }

    @PostMapping("/api/author")
    @Operation(summary = "Добавить")
    public Mono<AuthorDto> create(@RequestBody AuthorDto author) {
        return Mono.just(author)
                .doOnNext(d -> d.setId(null))
                .map(mapper::fromDto)
                .flatMap(repo::save)
                .map(mapper::toDto);
    }

    @PutMapping("/api/author/{id}")
    @Operation(summary = "Изменить")
    public Mono<AuthorDto> update(@PathVariable String id, @RequestBody AuthorDto author) {
        return Mono.just(author)
                .doOnNext(d -> d.setId(id))
                .map(mapper::fromDto)
                .flatMap(repo::save)
                .flatMap(
                        a -> mongoTemplate.updateMulti(
                                Query.query(Criteria.where("author.id").is(a.getId())),
                                Update.update("author", a),
                                Book.class
                        ).thenReturn(a)
                ).map(mapper::toDto);
    }

    @DeleteMapping("/api/author/{id}")
    @Operation(summary = "Удалить")
    public Mono<Void> delete(@PathVariable String id) {
        return repo.deleteById(id).then(bookRepo.deleteByAuthorId(id));
    }
}
