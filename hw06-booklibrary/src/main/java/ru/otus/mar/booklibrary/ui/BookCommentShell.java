package ru.otus.mar.booklibrary.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.service.BookCommentService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class BookCommentShell {

    private final MainShell mainShell;

    private final BookCommentService service;

    @ShellMethodAvailability(value = "isBookSelected")
    @ShellMethod(value = "add selected book comment", key = {"a c", "add comment"})
    public BookCommentDto add(@ShellOption(value = {"-c", "--comment"}) String comment) {
        return service.create(new BookCommentDto((BookDto) mainShell.getSelectedEntity(), comment));
    }

    @ShellMethodAvailability(value = "isBookSelected")
    @ShellMethod(value = "list selected book comment", key = {"l c", "list comment"})
    public List<String> list() {
        return service.getByBook((BookDto) mainShell.getSelectedEntity()).stream().map(BookCommentDto::comment)
                .toList();
    }

    private Availability isBookSelected() {
        return mainShell.isEntityClassSelected(BookDto.class);
    }
}
