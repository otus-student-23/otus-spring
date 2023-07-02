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
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.dto.SseDto;
import ru.otus.mar.booklibrary.mapper.GenreMapper;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import static com.mongodb.client.model.changestream.OperationType.DELETE;

@RestController
@RequiredArgsConstructor
@Tag(name = "Жанры", description = "Жанры")
public class GenreController {
    
    private final GenreRepository repo;

    private final GenreMapper mapper;

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping("/api/genre")
    @Operation(summary = "Полный список")
    public Flux<GenreDto> list() {
        return repo.findAll().map(mapper::toDto);
    }

    @GetMapping("/api/genre/stream")
    @Operation(summary = "Поток изменений")
    public Flux<SseDto<GenreDto>> stream() {
        return mongoTemplate.changeStream(Genre.class)
                .watchCollection("genre")
                .listen()
                .map(e -> new SseDto<>(
                        e.getOperationType().name(),
                        e.getOperationType() == DELETE
                                ? new GenreDto(((BsonObjectId) e.getRaw().getDocumentKey().get("_id"))
                                .getValue().toString(), null)
                                : mapper.toDto(e.getBody()))
                );
    }

    @GetMapping("/api/genre/{id}")
    @Operation(summary = "Получить")
    public Mono<GenreDto> get(@PathVariable String id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @PostMapping("/api/genre")
    @Operation(summary = "Добавить")
    public Mono<GenreDto> create(@RequestBody GenreDto genre) {
        genre.setId(null);
        return repo.save(mapper.fromDto(genre)).map(mapper::toDto);
    }

    @PutMapping("/api/genre/{id}")
    @Operation(summary = "Изменить")
    public Mono<GenreDto> update(@PathVariable String id, @RequestBody GenreDto genre) {
        genre.setId(id);
        return repo.save(mapper.fromDto(genre)).map(mapper::toDto)
                .doOnSuccess(a -> mongoTemplate.updateMulti(
                        Query.query(Criteria.where("genre.id").is(a.getId())),
                        Update.update("genre", genre),
                        Book.class
                ).subscribe());
    }

    @DeleteMapping("/api/genre/{id}")
    @Operation(summary = "Удалить")
    public Mono<Void> delete(@PathVariable String id) {
        return repo.deleteById(id)
                .doOnSuccess(a -> mongoTemplate.remove(
                        Query.query(Criteria.where("genre.id").is(id)), Book.class
                ).subscribe());
    }
}
