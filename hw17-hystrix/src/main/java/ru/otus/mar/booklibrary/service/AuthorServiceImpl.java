package ru.otus.mar.booklibrary.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.config.ApplicationProperties;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.exception.NotFoundException;
import ru.otus.mar.booklibrary.mapper.AuthorMapper;
import ru.otus.mar.booklibrary.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final ApplicationProperties appProps;

    private final AuthorRepository repo;

    private final AuthorMapper mapper;

    @Override
    public AuthorDto get(UUID id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public AuthorDto create(AuthorDto author) {
        return mapper.toDto(repo.findByName(author.getName()).orElseGet(() -> repo.save(mapper.toEntity(author))));
    }

    @Override
    public AuthorDto update(AuthorDto author) {
        return mapper.toDto(repo.save(mapper.toEntity(author)));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @HystrixCommand(fallbackMethod = "stubGetAll")
    @Override
    public List<AuthorDto> getAll() {
        if (appProps.getEmulateFault() && (new Random()).nextBoolean()) {
            throw new RuntimeException();
        }
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<AuthorDto> getByName(String name) {
        return repo.findByName(name).map(mapper::toDto);
    }

    private List<AuthorDto> stubGetAll() {
        return List.of(new AuthorDto("n/a"));
    }
}
