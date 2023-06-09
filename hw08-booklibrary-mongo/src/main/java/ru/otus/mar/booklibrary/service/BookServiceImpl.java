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
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

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
        return mapper.toDto(bookRepo.save(mapper.fromDto(book)));
    }

    @Override
    public void delete(BookDto book) {
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
        BooleanBuilder builder = new BooleanBuilder();
        if (filter.name() != null) {
            builder.and(QBook.book.name.eq(filter.name()));
        }
        if (filter.author() != null) {
            builder.and(QBook.book.author.name.eq(filter.author()));
        }
        if (filter.genre() != null) {
            builder.and(QBook.book.genre.name.eq(filter.genre()));
        }
        return ((List<Book>) bookRepo.findAll(builder.getValue())).stream().map(mapper::toDto).toList();
    }
}
