package ru.otus.mar.booklibrary.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.List;

@ShellComponent
public class AuthorShell {

    private final AuthorService authorService;

    public AuthorShell(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "add author", key = {"a a", "add author"})
    public void add(@ShellOption(value = {"-n", "--name"}) String name) {
        authorService.create(new AuthorDto(name));
    }

    @ShellMethod(value = "delete author", key = {"d a", "delete author"})
    public void delete(String name) {
        authorService.delete(new AuthorDto(name));
    }

    @ShellMethod(value = "get author", key = {"g a", "get author"})
    public AuthorDto get(String name) {
        return authorService.getByName(name).orElse(null);
    }

    @ShellMethod(value = "list author", key = {"l a", "list author"})
    public List<AuthorDto> list(
            @ShellOption(value = {"-f", "--filter"}, defaultValue = ShellOption.NULL) String filter) {
        return (filter == null) ? authorService.getAll() : authorService.getByFilter(filter);
    }
}