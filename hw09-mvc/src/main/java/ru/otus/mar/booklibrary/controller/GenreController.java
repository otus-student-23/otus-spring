package ru.otus.mar.booklibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping("/genre")
    public String list(Model model) {
        model.addAttribute("genres", service.getAll());
        return "genre/list";
    }

    @GetMapping("/genre/add")
    public String addPage(Model model) {
        return editPage(null, model);
    }

    @PostMapping("/genre/add")
    public String add(GenreDto genre) {
        return save(genre);
    }

    @GetMapping("/genre/edit")
    public String editPage(@RequestParam(required = false) UUID id, Model model) {
        model.addAttribute("genre", (id == null) ? new GenreDto() : service.get(id));
        return "genre/edit";
    }

    @PostMapping("/genre/edit")
    public String save(GenreDto genre) {
        service.update(genre);
        return "redirect:/genre";
    }

    @GetMapping("/genre/delete")
    public String delete(@RequestParam UUID id) {
        service.delete(id);
        return "redirect:/genre";
    }
}
