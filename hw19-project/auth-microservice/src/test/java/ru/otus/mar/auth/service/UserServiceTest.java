package ru.otus.mar.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.mar.auth.dto.UserDto;
import ru.otus.mar.auth.mapper.UserMapperImpl;
import ru.otus.mar.auth.model.User;
import ru.otus.mar.auth.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserServiceImpl.class, UserMapperImpl.class})
@ActiveProfiles({"test"})
public class UserServiceTest {

    private static final UserDto USER_DTO =
            new UserDto(UUID.fromString("58f3c049-4132-400a-ae4e-eac4b558ffa9"), "User_1", "user");

    private static final User USER =
            new User(UUID.fromString("58f3c049-4132-400a-ae4e-eac4b558ffa9"), "User_1", "user");

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repo;

    @Test
    void create() {
        when(repo.save(USER)).thenReturn(USER);
        assertEquals(USER_DTO, service.create(USER_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(USER);
        assertEquals(USER_DTO, service.update(USER_DTO));
    }

    @Test
    void delete() {
        service.delete(USER_DTO.getId());
        verify(repo, times(1)).deleteById(USER.getId());
    }

    @Test
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(USER));
        assertTrue(service.getAll().containsAll(List.of(USER_DTO)));
    }
}
