package ru.otus.mar.booklibrary.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;

@ShellComponent
public class GenreShell {

    private final GenreService genreService;

    public GenreShell(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod(value = "add genre", key = {"a g", "add genre"})
    public void add(@ShellOption(value = {"-n", "--name"}) String name) {
        genreService.create(new GenreDto(name));
    }

    @ShellMethod(value = "delete genre", key = {"d g", "delete genre"})
    public void delete(String name) {
        genreService.delete(new GenreDto(name));
    }

    @ShellMethod(value = "get genre", key = {"g g", "get genre"})
    public GenreDto get(String name) {
        return genreService.getByName(name).orElse(null);
    }

    @ShellMethod(value = "list genre", key = {"l g", "list genre"})
    public List<GenreDto> list(
            @ShellOption(value = {"-f", "--filter"}, defaultValue = ShellOption.NULL) String filter) {
        return (filter == null) ? genreService.getAll() : genreService.getByFilter(filter);
    }
}