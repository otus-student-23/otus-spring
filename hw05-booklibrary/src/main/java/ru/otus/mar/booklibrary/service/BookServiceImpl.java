package ru.otus.mar.booklibrary.service;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dao.AuthorDao;
import ru.otus.mar.booklibrary.dao.BookDao;
import ru.otus.mar.booklibrary.dao.GenreDao;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.BookMapper;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookDao bookDao, BookMapper bookMapper, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void create(BookDto book) {
        Book newBook = bookMapper.fromDto(book);
        newBook.setAuthor(authorDao.getByName(book.author())
                .orElseGet(() -> authorDao.create(new Author(book.author()))));
        newBook.setGenre(genreDao.getByName(book.genre()).orElseGet(() -> genreDao.create(new Genre(book.genre()))));
        bookDao.create(newBook);
    }

    @Override
    public void delete(BookDto book) {
        bookDao.deleteById(
                bookDao.getByNameAndAuthor(book.name(), authorDao.getByName(book.author()).get()).get().getId());
    }

    @Override
    public List<BookDto> getAll() {
        return bookDao.getAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    public Optional<BookDto> getByNameAndAuthor(String name, String author) {
        return bookDao.getByNameAndAuthor(name, authorDao.getByName(author).get()).map(bookMapper::toDto);
    }

    @Override
    public List<BookDto> getByFilter(String filter) {
        return bookDao.getByFilter(filter).stream().map(bookMapper::toDto).toList();
    }
}
