package ru.otus.mar.booklibrary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.BsonObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.SseDto;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import static com.mongodb.client.model.changestream.OperationType.DELETE;

@RestController
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Книги")
public class BookController {

    private final BookRepository bookRepo;

    private final BookMapper mapper;

    private final AuthorRepository authorRepo;

    private final GenreRepository genreRepo;

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping("/api/book")
    @Operation(summary = "Полный список")
    public Flux<BookDto> list() {
        return bookRepo.findAll().map(mapper::toDto);
    }

    @GetMapping("/api/book/stream")
    @Operation(summary = "Поток изменений")
    public Flux<SseDto<BookDto>> stream() {
        return mongoTemplate.changeStream(Book.class)
                .watchCollection("book")
                .listen()
                .map(e -> new SseDto<>(
                        e.getOperationType().name(),
                        e.getOperationType() == DELETE
                                ? new BookDto(((BsonObjectId) e.getRaw().getDocumentKey().get("_id"))
                                .getValue().toString(), null, null, null)
                                : mapper.toDto(e.getBody()))
                );
    }

    @GetMapping("/api/book/{id}")
    @Operation(summary = "Получить")
    public Mono<BookDto> get(@PathVariable String id) {
        return bookRepo.findById(id).map(mapper::toDto);
    }

    @PostMapping("/api/book")
    @Operation(summary = "Добавить")
    public Mono<BookDto> create(@RequestBody BookDto book) {
        return update(null, book);
    }

    @PutMapping("/api/book/{id}")
    @Operation(summary = "Изменить")
    public Mono<BookDto> update(@PathVariable String id, @RequestBody() BookDto dto) {
        Book book = mapper.fromDto(dto);
        book.setId(id);
        return authorRepo.findByName(book.getAuthor().getName())
                .switchIfEmpty(authorRepo.insert(book.getAuthor()))
                .doOnNext(book::setAuthor)
                .flatMap(a -> genreRepo.findByName(book.getGenre().getName()))
                .switchIfEmpty(genreRepo.insert(book.getGenre()))
                .doOnNext(book::setGenre)
                .flatMap(g -> bookRepo.save(book))
                .map(mapper::toDto);
    }

    @DeleteMapping("/api/book/{id}")
    @Operation(summary = "Удалить")
    public Mono<Void> delete(@PathVariable String id) {
        return bookRepo.deleteById(id)
                .doOnSuccess(a -> mongoTemplate.remove(
                        Query.query(Criteria.where("book.id").is(id)), BookComment.class
                ).subscribe());
    }
}
