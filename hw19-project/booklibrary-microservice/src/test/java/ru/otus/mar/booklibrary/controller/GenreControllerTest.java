package ru.otus.mar.booklibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
@ActiveProfiles({"test"})
public class GenreControllerTest {

    private static final GenreDto GENRE_DTO =
            new GenreDto(UUID.fromString("301c28f7-1793-45dd-91a1-8c0ec82d5beb"), "genre_a");

    private static final RequestPostProcessor MOCK_JWT = jwt()
            .authorities(List.of(new SimpleGrantedAuthority("user")))
            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user"));

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void unauthorized() throws Exception {
        mvc.perform(get("/api/library/genre"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void list() throws Exception {
        when(service.getAll()).thenReturn(List.of(GENRE_DTO));
        mvc.perform(get("/api/library/genre").with(MOCK_JWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(GENRE_DTO))))
                .andReturn();
    }

    @Test
    public void apiPost() throws Exception {
        when(service.create(any(GenreDto.class))).thenReturn(GENRE_DTO);
        mvc.perform(post("/api/library/genre")
                        .with(MOCK_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(GENRE_DTO)))
                .andReturn();
    }

    @Test
    public void apiPut() throws Exception {
        when(service.update(any(GenreDto.class))).thenReturn(GENRE_DTO);
        mvc.perform(put("/api/library/genre/" + GENRE_DTO.getId())
                        .with(MOCK_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(GENRE_DTO)))
                .andReturn();
    }

    @Test
    public void apiDelete() throws Exception {
        mvc.perform(delete("/api/library/genre/" + GENRE_DTO.getId())
                        .with(MOCK_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_DTO)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).delete(GENRE_DTO.getId());
    }
}
