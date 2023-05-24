package ru.otus.mar.booklibrary.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.BookFilterDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.QBook;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

    private final BookCommentRepository bookCommentRepo;

    private final BookMapper mapper;

    private final AuthorService authorService;

    private final AuthorRepository authorRepo;

    private final AuthorMapper authorMapper;

    private final GenreService genreService;

    private final GenreRepository genreRepo;

    @Override
    public BookDto create(BookDto book) {
        return update(book);
    }

    @Override
    @Transactional
    public BookDto update(BookDto book) {
        book.setAuthor(authorService.create(book.getAuthor()));
        book.setGenre(genreService.create(book.getGenre()));
        return mapper.toDto(bookRepo.save(mapper.fromDto(book)));
    }

    @Override
    @Transactional
    public void delete(BookDto book) {
        bookCommentRepo.deleteByBook(mapper.fromDto(book));
        bookRepo.deleteById(book.getId());
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<BookDto> getByNameAndAuthor(String name, AuthorDto author) {
        return bookRepo.findByNameAndAuthor(name, authorMapper.fromDto(author)).map(mapper::toDto);
    }

    @Override
    public List<BookDto> getByFilter(BookFilterDto filter) {
        QBook qBook = new QBook("book");
        BooleanBuilder builder = new BooleanBuilder();
        if (filter.name() != null) {
            builder.and(qBook.name.eq(filter.name()));
        }
        if (filter.author() != null) {
            builder.and(qBook.author.eq(authorRepo.findByName(filter.author()).get()));
        }
        if (filter.genre() != null) {
            builder.and(qBook.genre.eq(genreRepo.findByName(filter.genre()).get()));
        }
        return ((List<Book>) bookRepo.findAll(builder.getValue())).stream().map(mapper::toDto).toList();
    }
}
