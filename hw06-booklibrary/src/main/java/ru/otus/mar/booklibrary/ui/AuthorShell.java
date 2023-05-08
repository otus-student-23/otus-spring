package ru.otus.mar.booklibrary.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class AuthorShell {

    private final EntityProvider prompt;

    private final AuthorService service;

    @ShellMethod(value = "add author", key = {"a a", "add author"})
    public AuthorDto add(@ShellOption(value = {"-n", "--name"}) String name) {
        return service.create(new AuthorDto(name));
    }

    @ShellMethod(value = "get author", key = {"g a", "get author"})
    public AuthorDto get(@ShellOption(value = {"-n", "--name"}) String name) {
        AuthorDto author = service.getByName(name).get();
        prompt.selectEntity(author, author.name());
        return author;
    }

    @ShellMethod(value = "list author", key = {"l a", "list author"})
    public List<String> list() {
        return service.getAll().stream().map(AuthorDto::name).toList();
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "update selected author", key = {"u a", "update author"})
    public AuthorDto update(@ShellOption(value = {"-n", "--name"}) String name) {
        AuthorDto author = service.update(new AuthorDto(((AuthorDto) prompt.getSelectedEntity()).id(), name));
        prompt.selectEntity(author, author.name());
        return author;
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "delete selected author", key = {"d a", "delete author"})
    public void delete() {
        service.delete((AuthorDto) prompt.getSelectedEntity());
        prompt.reset();
    }

    private Availability isEntitySelected() {
        return prompt.isEntityClassSelected(AuthorDto.class);
    }
}