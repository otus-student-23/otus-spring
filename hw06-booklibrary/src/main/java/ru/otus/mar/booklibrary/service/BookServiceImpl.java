package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dao.BookDao;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.mapper.BookMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao dao;

    private final BookMapper mapper;

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    private final GenreService genreService;

    @Override
    @Transactional
    public BookDto create(BookDto book) {
        book.setAuthor(authorService.create(book.getAuthor()));
        book.setGenre(genreService.create(book.getGenre()));
        return mapper.toDto(dao.insert(mapper.fromDto(book)));
    }

    @Override
    @Transactional
    public BookDto update(BookDto book) {
        book.setAuthor(authorService.create(book.getAuthor()));
        book.setGenre(genreService.create(book.getGenre()));
        return mapper.toDto(dao.update(mapper.fromDto(book)));
    }

    @Override
    @Transactional
    public void delete(BookDto book) {
        dao.delete(mapper.fromDto(book));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return dao.getAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getByNameAndAuthor(String name, AuthorDto author) {
        return dao.getByNameAndAuthor(name, authorMapper.fromDto(author)).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getByName(String name) {
        return dao.getByName(name).stream().map(mapper::toDto).toList();
    }
}
