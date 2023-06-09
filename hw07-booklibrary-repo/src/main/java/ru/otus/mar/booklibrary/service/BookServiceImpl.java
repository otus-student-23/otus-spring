package ru.otus.mar.booklibrary.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.BookFilterDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    private final BookMapper mapper;

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    private final GenreService genreService;

    @Override
    public BookDto create(BookDto book) {
        return update(book);
    }

    @Override
    @Transactional
    public BookDto update(BookDto book) {
        book.setAuthor(authorService.create(book.getAuthor()));
        book.setGenre(genreService.create(book.getGenre()));
        return mapper.toDto(repo.save(mapper.fromDto(book)));
    }

    @Override
    public void delete(BookDto book) {
        repo.deleteById(book.getId());
    }

    @Override
    public List<BookDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<BookDto> getByNameAndAuthor(String name, AuthorDto author) {
        return repo.findByNameAndAuthor(name, authorMapper.fromDto(author)).map(mapper::toDto);
    }

    @Override
    public List<BookDto> getByFilter(BookFilterDto filter) {
        return repo.findAll((Specification<Book>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.name() != null) {
                predicates.add(builder.equal(root.get("name"), filter.name()));
            }
            if (filter.author() != null) {
                predicates.add(builder.equal(root.get("author").get("name"), filter.author()));
            }
            if (filter.genre() != null) {
                predicates.add(builder.equal(root.get("genre").get("name"), filter.genre()));
            }
            return builder.and(predicates.toArray(Predicate[]::new));
        }).stream().map(mapper::toDto).toList();
    }
}
