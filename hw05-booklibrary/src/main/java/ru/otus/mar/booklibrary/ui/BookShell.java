package ru.otus.mar.booklibrary.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.service.BookService;

import java.util.List;

@ShellComponent
public class BookShell {

    private final BookService bookService;

    public BookShell(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "add book", key = {"a b", "add book"})
    public void add(
            @ShellOption(value = {"-n", "--name"}) String name,
            @ShellOption(value = {"-a", "--author"}) String author,
            @ShellOption(value = {"-g", "--genre"}) String genre
    ) {
        bookService.create(new BookDto(name, author, genre));
    }

    @ShellMethod(value = "delete book", key = {"d b", "delete book"})
    public void delete(
            @ShellOption(value = {"-n", "--name"}) String name,
            @ShellOption(value = {"-a", "--author"}) String author
    ) {
        bookService.delete(new BookDto(name, author, null));
    }

    @ShellMethod(value = "get book", key = {"g b", "get book"})
    public BookDto get(
            @ShellOption(value = {"-n", "--name"}) String name,
            @ShellOption(value = {"-a", "--author"}) String author
    ) {
        return bookService.getByNameAndAuthor(name, author).orElse(null);
    }

    @ShellMethod(value = "list book", key = {"l b", "list book"})
    public List<BookDto> list(
            @ShellOption(value = {"-f", "--filter"}, defaultValue = ShellOption.NULL) String filter) {
        return (filter == null) ? bookService.getAll() : bookService.getByFilter(filter);
    }
}