package ru.otus.mar.booklibrary.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.AuthorService;
import ru.otus.mar.booklibrary.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class BookShell {

    private final EntityProvider prompt;

    private final BookService bookService;

    private final AuthorService authorService;

    @ShellMethod(value = "add book", key = {"a b", "add book"})
    public BookDto add(
            @ShellOption(value = {"-n", "--name"}) String name,
            @ShellOption(value = {"-a", "--author"}) String author,
            @ShellOption(value = {"-g", "--genre"}) String genre
    ) {
        return bookService.create(new BookDto(name, new AuthorDto(author), new GenreDto(genre)));
    }

    @ShellMethod(value = "get book", key = {"g b", "get book"})
    public BookDto get(
            @ShellOption(value = {"-n", "--name"}) String name,
            @ShellOption(value = {"-a", "--author"}) String author
    ) {
        BookDto book = bookService.getByNameAndAuthor(name, authorService.getByName(author).get()).get();
        prompt.selectEntity(book, book.getName());
        return book;
    }

    @ShellMethod(value = "list book", key = {"l b", "list book"})
    public List<List<String>> list(
            @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name,
            @ShellOption(value = {"-a", "--author"}, defaultValue = ShellOption.NULL) String author,
            @ShellOption(value = {"-g", "--genre"}, defaultValue = ShellOption.NULL) String genre
    ) {
        List<BookDto> result = (name == null) ? bookService.getAll() : bookService.getByName(name);

        //TODO перенести фильтры ниже из стримов в селекты
        if (author != null) {
            result = result.stream().filter(b -> author.equals(b.getAuthor().name())).toList();
        }
        if (genre != null) {
            result = result.stream().filter(b -> genre.equals(b.getGenre().name())).toList();
        }
        return result.stream().map(b -> List.of(b.getName(), b.getAuthor().name(), b.getGenre().name())).toList();
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "update selected book", key = {"u b", "update book"})
    public BookDto update(
            @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name,
            @ShellOption(value = {"-a", "--author"}, defaultValue = ShellOption.NULL) String author,
            @ShellOption(value = {"-g", "--genre"}, defaultValue = ShellOption.NULL) String genre
    ) {
        BookDto entity = (BookDto) prompt.getSelectedEntity();
        BookDto book = bookService.update(new BookDto(
                entity.getId(),
                name == null ? entity.getName() : name,
                author == null ? entity.getAuthor() : new AuthorDto(author),
                genre == null ? entity.getGenre() : new GenreDto(genre)
        ));
        prompt.selectEntity(book, book.getName());
        return book;
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "delete selected book", key = {"d b", "delete book"})
    public void delete() {
        bookService.delete((BookDto) prompt.getSelectedEntity());
        prompt.reset();
    }

    private Availability isEntitySelected() {
        return prompt.isEntityClassSelected(BookDto.class);
    }
}