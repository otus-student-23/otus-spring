package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dao.BookCommentDao;
import ru.otus.mar.booklibrary.dao.BookDao;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final BookCommentDao bookCommentDao;

    private final BookMapper bookMapper;

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    private final GenreService genreService;

    @Override
    @Transactional
    public BookDto create(BookDto book) {
        book.setAuthor(authorService.create(book.getAuthor()));
        book.setGenre(genreService.create(book.getGenre()));
        return bookMapper.toDto(bookDao.insert(bookMapper.fromDto(book)));
    }

    @Override
    @Transactional
    public BookDto update(BookDto book) {
        book.setAuthor(authorService.create(book.getAuthor()));
        book.setGenre(genreService.create(book.getGenre()));
        return bookMapper.toDto(bookDao.update(bookMapper.fromDto(book)));
    }

    @Override
    @Transactional
    public void delete(BookDto book) {
        Book deleteBook = bookMapper.fromDto(book);
        bookCommentDao.deleteByBook(deleteBook);
        bookDao.delete(deleteBook);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookDao.getAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getByNameAndAuthor(String name, AuthorDto author) {
        return bookDao.getByNameAndAuthor(name, authorMapper.fromDto(author)).map(bookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getByName(String name) {
        return bookDao.getByName(name).stream().map(bookMapper::toDto).toList();
    }
}
