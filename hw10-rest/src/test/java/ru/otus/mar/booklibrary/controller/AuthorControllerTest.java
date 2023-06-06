package ru.otus.mar.booklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.rest.AuthorController;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    private static final AuthorDto AUTHOR_DTO =
            new AuthorDto(UUID.fromString("301c28f7-1793-45dd-91a1-8c0ec82d5beb"), "author_a");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService service;

    @Test
    public void list() throws Exception {
        when(service.getAll()).thenReturn(List.of(AUTHOR_DTO));
        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/list"))
                .andExpect(model().attribute("authors", List.of(AUTHOR_DTO)));
    }

    @Test
    public void add() throws Exception {
        mvc.perform(post("/author/add")
                        .flashAttr("author", AUTHOR_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author"));
        verify(service, times(1)).update(any(AuthorDto.class));
    }

    @Test
    public void editPage() throws Exception {
        when(service.get(AUTHOR_DTO.getId())).thenReturn(AUTHOR_DTO);
        mvc.perform(get("/author/edit?id=" + AUTHOR_DTO.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("author/edit"))
                .andExpect(model().attribute("author", AUTHOR_DTO));
    }

    @Test
    public void edit() throws Exception {
        mvc.perform(post("/author/edit")
                        .flashAttr("author", AUTHOR_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author"));
        verify(service, times(1)).update(any(AuthorDto.class));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(get("/author/delete?id=" + AUTHOR_DTO.getId())
                        .flashAttr("author", AUTHOR_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author"));
        verify(service, times(1)).delete(AUTHOR_DTO.getId());
    }
}
