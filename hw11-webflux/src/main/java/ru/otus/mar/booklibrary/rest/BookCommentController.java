package ru.otus.mar.booklibrary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.BsonObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.SseDto;
import ru.otus.mar.booklibrary.mapper.BookCommentMapper;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;

import static com.mongodb.client.model.changestream.OperationType.DELETE;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарии к книге", description = "Комментарии к книге")
public class BookCommentController {

    private final BookCommentRepository repo;

    private final BookCommentMapper mapper;

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping("/api/book/{bookId}/comment")
    @Operation(summary = "Полный список")
    public Flux<BookCommentDto> list(@PathVariable String bookId) {
        return repo.findByBookId(bookId).map(mapper::toDto);
    }

    @GetMapping("/api/book/{bookId}/comment/stream")
    @Operation(summary = "Поток изменений")
    public Flux<SseDto<BookCommentDto>> stream(@PathVariable String bookId) {
        return mongoTemplate.changeStream(BookComment.class)
                .watchCollection("book_comment")
                .filter(where("book").is(bookId))
                .listen()
                .map(e -> new SseDto<>(
                        e.getOperationType().name(),
                        e.getOperationType() == DELETE
                                ? new BookCommentDto(((BsonObjectId) e.getRaw().getDocumentKey().get("_id"))
                                .getValue().toString(), null, null)
                                : mapper.toDto(e.getBody()))
                );
    }

    @GetMapping("/api/book/{bookId}/comment/{id}")
    @Operation(summary = "Получить")
    public Mono<BookCommentDto> get(@PathVariable String bookId, @PathVariable String id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @PostMapping("/api/book/{bookId}/comment")
    @Operation(summary = "Добавить")
    public Mono<BookCommentDto> create(@PathVariable String bookId, @RequestBody BookCommentDto comment) {
        return Mono.just(comment)
                .doOnNext(d -> d.setId(null))
                .doOnNext(d -> d.setBook(new BookDto(bookId, null, null, null)))
                .map(mapper::fromDto)
                .flatMap(repo::save)
                .map(mapper::toDto);
    }

    @PutMapping("/api/book/{bookId}/comment/{id}")
    @Operation(summary = "Изменить")
    public Mono<BookCommentDto> update(@PathVariable String id, @RequestBody BookCommentDto comment) {
        return Mono.just(comment)
                .doOnNext(d -> d.setId(id))
                .map(mapper::fromDto)
                .flatMap(repo::save)
                .map(mapper::toDto);
    }

    @DeleteMapping("/api/book/{bookId}/comment/{id}")
    @Operation(summary = "Удалить")
    public Mono<Void> delete(@PathVariable String id) {
        return repo.deleteById(id);
    }
}
