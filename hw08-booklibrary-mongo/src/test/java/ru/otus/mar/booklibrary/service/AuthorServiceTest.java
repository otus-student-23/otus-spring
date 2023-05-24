package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Author_1");

    private static final Author AUTHOR = new Author("Author_1");

    @InjectMocks
    private AuthorServiceImpl service;

    @Mock
    private AuthorRepository repo;

    @Mock
    private AuthorMapper mapper;

    @Test
    void create() {
        when(repo.findByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        when(mapper.toDto(AUTHOR)).thenReturn(AUTHOR_DTO);
        assertEquals(AUTHOR_DTO, service.create(AUTHOR_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(AUTHOR);
        when(mapper.toDto(AUTHOR)).thenReturn(AUTHOR_DTO);
        when(mapper.fromDto(AUTHOR_DTO)).thenReturn(AUTHOR);
        assertEquals(AUTHOR_DTO, service.update(AUTHOR_DTO));
    }

    @Test
    void delete() {
        service.delete(AUTHOR_DTO);
        verify(repo, times(1)).deleteById(AUTHOR.getId());
    }

    @Test
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(AUTHOR));
        when(mapper.toDto(AUTHOR)).thenReturn(AUTHOR_DTO);
        assertTrue(service.getAll().containsAll(List.of(AUTHOR_DTO)));
    }

    @Test
    void getByName() {
        when(repo.findByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        when(mapper.toDto(AUTHOR)).thenReturn(AUTHOR_DTO);
        assertEquals(AUTHOR_DTO, service.getByName(AUTHOR.getName()).get());
    }
}
