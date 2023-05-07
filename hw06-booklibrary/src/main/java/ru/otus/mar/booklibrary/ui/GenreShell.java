package ru.otus.mar.booklibrary.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class GenreShell {

    private final MainShell mainShell;

    private final GenreService service;

    @ShellMethod(value = "add genre", key = {"a g", "add genre"})
    public GenreDto add(@ShellOption(value = {"-n", "--name"}) String name) {
        return service.create(new GenreDto(name));
    }

    @ShellMethod(value = "get genre", key = {"g g", "get genre"})
    public GenreDto get(@ShellOption(value = {"-n", "--name"}) String name) {
        GenreDto genre = service.getByName(name).get();
        mainShell.selectEntity(genre, genre.name());
        return genre;
    }

    @ShellMethod(value = "list genre", key = {"l g", "list genre"})
    public List<String> list() {
        return service.getAll().stream().map(GenreDto::name).toList();
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "update selected genre", key = {"u g", "update genre"})
    public GenreDto update(@ShellOption(value = {"-n", "--name"}) String name) {
        GenreDto genre = service.update(new GenreDto(((GenreDto) mainShell.getSelectedEntity()).id(), name));
        mainShell.selectEntity(genre, genre.name());
        return genre;
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "delete selected genre", key = {"d g", "delete genre"})
    public void delete() {
        service.delete((GenreDto) mainShell.getSelectedEntity());
        mainShell.selectEntity(null, null);
    }

    private Availability isEntitySelected() {
        return mainShell.isEntityClassSelected(GenreDto.class);
    }
}