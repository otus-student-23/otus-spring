package ru.otus.mar.booklibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.service.BookCommentService;
import ru.otus.mar.booklibrary.service.BookService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BookCommentController {

    private final BookCommentService service;

    private final BookService bookService;

    @GetMapping("/comment")
    public String list(@RequestParam UUID bookId, Model model) {
        model.addAttribute("comments", service.getByBookId(bookId))
                .addAttribute("book", bookService.get(bookId));
        return "comment/list";
    }

    @GetMapping("/comment/add")
    public String addPage(@RequestParam UUID bookId, Model model) {
        model.addAttribute("comment", new BookCommentDto(bookService.get(bookId), null));
        return "comment/edit";
    }

    @PostMapping("/comment/add")
    public String add(BookCommentDto comment) {
        return save(comment);
    }

    @GetMapping("/comment/edit")
    public String editPage(@RequestParam UUID id, Model model) {
        model.addAttribute("comment", service.get(id));
        return "comment/edit";
    }

    @PostMapping("/comment/edit")
    public String save(BookCommentDto comment) {
        service.update(comment);
        return String.format("redirect:/comment?bookId=%s", comment.getBook().getId());
    }

    @GetMapping("/comment/delete")
    public String delete(@RequestParam UUID id) {
        service.delete(id);
        return "redirect:/book";
    }
}
