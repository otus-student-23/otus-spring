package ru.otus.mar.booklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.AuthorService;
import ru.otus.mar.booklibrary.service.BookService;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO @WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final BookDto BOOK_DTO = new BookDto(
            UUID.fromString("301c28f7-1793-45dd-91a1-8c0ec82d5beb"),
            "book_a",
            new AuthorDto("author_a"),
            new GenreDto("genre_a"));

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService service;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    public void list() throws Exception {
        when(service.getAll()).thenReturn(List.of(BOOK_DTO));
        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", List.of(BOOK_DTO)));
    }

    @Test
    public void add() throws Exception {
        mvc.perform(post("/book/add")
                        .flashAttr("book", BOOK_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
        verify(service, times(1)).update(any(BookDto.class));
    }

    @Test
    public void editPage() throws Exception {
        when(service.get(BOOK_DTO.getId())).thenReturn(BOOK_DTO);
        mvc.perform(get("/book/edit?id=" + BOOK_DTO.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", BOOK_DTO));
    }

    @Test
    public void edit() throws Exception {
        mvc.perform(post("/book/edit")
                        .flashAttr("book", BOOK_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
        verify(service, times(1)).update(any(BookDto.class));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(get("/book/delete?id=" + BOOK_DTO.getId())
                        .flashAttr("book", BOOK_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
        verify(service, times(1)).delete(BOOK_DTO.getId());
    }
}
