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

    @ShellMethodAvailability(value = "isBookSelected")
    @ShellMethod(value = "get selected book comment", key = {"g c", "get comment"})
    public BookCommentDto add(@ShellOption(value = {"-n", "--number"}) int num) {
        //TODO оптимизировать поиск комментария
        BookCommentDto comment = service.getByBook((BookDto) mainShell.getSelectedEntity()).get(num);
        mainShell.selectEntity(comment, comment.comment());
        return comment;
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "update selected comment", key = {"u c", "update comment"})
    public BookCommentDto update(@ShellOption(value = {"-c", "--comment"}) String comment) {
        BookCommentDto selComment = (BookCommentDto) mainShell.getSelectedEntity();
        BookCommentDto updComment = service.update(new BookCommentDto(selComment.id(), selComment.book(), comment));
        mainShell.selectEntity(updComment, updComment.comment());
        return updComment;
    }

    @ShellMethodAvailability(value = "isEntitySelected")
    @ShellMethod(value = "delete selected comment", key = {"d c", "delete comment"})
    public void delete() {
        service.delete((BookCommentDto) mainShell.getSelectedEntity());
        mainShell.reset();
    }

    private Availability isBookSelected() {
        return mainShell.isEntityClassSelected(BookDto.class);
    }

    private Availability isEntitySelected() {
        return mainShell.isEntityClassSelected(BookCommentDto.class);
    }
}
