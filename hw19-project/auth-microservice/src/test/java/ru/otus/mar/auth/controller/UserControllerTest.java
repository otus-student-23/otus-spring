package ru.otus.mar.auth.controller;

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
import ru.otus.mar.auth.dto.UserDto;
import ru.otus.mar.auth.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ActiveProfiles({"test"})
public class UserControllerTest {

    private static final UserDto USER_DTO =
            new UserDto(UUID.fromString("301c28f7-1793-45dd-91a1-8c0ec82d5beb"), "admin", "admin");

    private static final RequestPostProcessor MOCK_JWT = jwt()
            .authorities(List.of(new SimpleGrantedAuthority("admin")))
            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin"));

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void unauthorized() throws Exception {
        mvc.perform(get("/api/auth/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void list() throws Exception {
        when(service.getAll()).thenReturn(List.of(USER_DTO));
        mvc.perform(get("/api/auth/user").with(MOCK_JWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(USER_DTO))))
                .andReturn();
    }

    @Test
    public void apiPost() throws Exception {
        when(service.create(any(UserDto.class))).thenReturn(USER_DTO);
        mvc.perform(post("/api/auth/user")
                        .with(MOCK_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(USER_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(USER_DTO)))
                .andReturn();
    }

    @Test
    public void apiPut() throws Exception {
        when(service.update(any(UserDto.class))).thenReturn(USER_DTO);
        mvc.perform(put("/api/auth/user/" + USER_DTO.getId())
                        .with(MOCK_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(USER_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(USER_DTO)))
                .andReturn();
    }

    @Test
    public void apiDelete() throws Exception {
        mvc.perform(delete("/api/auth/user/" + USER_DTO.getId())
                        .with(MOCK_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(USER_DTO)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).delete(USER_DTO.getId());
    }
}
