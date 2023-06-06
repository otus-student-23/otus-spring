package ru.otus.mar.booklibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/author")
    public String list(Model model) {
        model.addAttribute("authors", service.getAll());
        return "author/list";
    }

    @GetMapping("/author/add")
    public String addPage(Model model) {
        return editPage(null, model);
    }

    @PostMapping("/author/add")
    public String add(AuthorDto author) {
        return save(author);
    }

    @GetMapping("/author/edit")
    public String editPage(@RequestParam(required = false) UUID id, Model model) {
        model.addAttribute("author", (id == null) ? new AuthorDto() : service.get(id));
        return "author/edit";
    }

    @PostMapping("/author/edit")
    public String save(AuthorDto author) {
        service.update(author);
        return "redirect:/author";
    }

    @GetMapping("/author/delete")
    public String delete(@RequestParam UUID id) {
        service.delete(id);
        return "redirect:/author";
    }
}
