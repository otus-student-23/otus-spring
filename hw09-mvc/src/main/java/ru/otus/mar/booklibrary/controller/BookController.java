package ru.otus.mar.booklibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.AuthorService;
import ru.otus.mar.booklibrary.service.BookService;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/book")
    public String list(Model model) {
        model.addAttribute("books", service.getAll());
        return "book/list";
    }

    @GetMapping("/book/add")
    public String addPage(Model model) {
        return editPage(null, model);
    }

    @PostMapping("/book/add")
    public String add(BookDto book) {
        return save(book);
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam(required = false) UUID id, Model model) {
        BookDto book = (id == null) ? new BookDto(null, new AuthorDto(), new GenreDto()) : service.get(id);
        model.addAttribute("book", book)
                .addAttribute("authors", authorService.getAll())
                .addAttribute("genres", genreService.getAll());
        return "book/edit";
    }

    @PostMapping("/book/edit")
    public String save(BookDto book) {
        service.update(book);
        return "redirect:/book";
    }

    @GetMapping("/book/delete")
    public String delete(@RequestParam UUID id) {
        service.delete(id);
        return "redirect:/book";
    }
}
