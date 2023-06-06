package ru.otus.mar.booklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO @WebMvcTest(GenreController.class)
public class GenreControllerTest {

    private static final GenreDto GENRE_DTO =
            new GenreDto(UUID.fromString("301c28f7-1793-45dd-91a1-8c0ec82d5beb"), "genre_a");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService service;

    @Test
    public void list() throws Exception {
        when(service.getAll()).thenReturn(List.of(GENRE_DTO));
        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/list"))
                .andExpect(model().attribute("genres", List.of(GENRE_DTO)));
    }

    @Test
    public void add() throws Exception {
        mvc.perform(post("/genre/add")
                        .flashAttr("genre", GENRE_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/genre"));
        verify(service, times(1)).update(any(GenreDto.class));
    }

    @Test
    public void editPage() throws Exception {
        when(service.get(GENRE_DTO.getId())).thenReturn(GENRE_DTO);
        mvc.perform(get("/genre/edit?id=" + GENRE_DTO.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/edit"))
                .andExpect(model().attribute("genre", GENRE_DTO));
    }

    @Test
    public void edit() throws Exception {
        mvc.perform(post("/genre/edit")
                        .flashAttr("genre", GENRE_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/genre"));
        verify(service, times(1)).update(any(GenreDto.class));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(get("/genre/delete?id=" + GENRE_DTO.getId())
                        .flashAttr("genre", GENRE_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/genre"));
        verify(service, times(1)).delete(GENRE_DTO.getId());
    }
}
