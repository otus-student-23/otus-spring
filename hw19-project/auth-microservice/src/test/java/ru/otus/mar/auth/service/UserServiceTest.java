package ru.otus.mar.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.BasePersistenceTest;
import ru.otus.mar.auth.dto.UserDto;
import ru.otus.mar.auth.model.User;
import ru.otus.mar.auth.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest extends BasePersistenceTest {
/*todo
    private static final UserDto USER_DTO = new UserDto("User_1");

    private static final User USER = new User("Author_1");

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repo;

    @Test
    void create() {
        when(repo.findByName(USER.getName())).thenReturn(Optional.of(USER));
        assertEquals(USER_DTO, service.create(USER_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(USER);
        assertEquals(USER_DTO, service.update(USER_DTO));
    }

    @Test
    void delete() {
        when(repo.findByName(USER.getName())).thenReturn(Optional.of(USER));
        service.delete(USER_DTO.getId());
        verify(repo, times(1)).deleteById(USER.getId());
    }

    @Test
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(USER));
        assertTrue(service.getAll().containsAll(List.of(USER_DTO)));
    }

    @Test
    void getByName() {
        when(repo.findByName(USER.getName())).thenReturn(Optional.of(USER));
        assertEquals(USER_DTO, service.getByName(USER.getName()).get());
    }*/
}
