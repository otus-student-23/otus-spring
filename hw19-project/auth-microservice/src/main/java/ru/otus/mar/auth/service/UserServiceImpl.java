package ru.otus.mar.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.auth.dto.UserDto;
import ru.otus.mar.auth.exception.NotFoundException;
import ru.otus.mar.auth.mapper.UserMapper;
import ru.otus.mar.auth.model.User;
import ru.otus.mar.auth.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    private final UserMapper mapper;

    @Override
    public UserDto get(UUID id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDto create(UserDto author) {
        return update(author);
    }

    @Override
    public UserDto update(UserDto author) {
        return mapper.toDto(repo.save(mapper.toEntity(author)));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public List<UserDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public String getRoles(UUID id, String username) {
        return repo.findById(id).orElseGet(() -> repo.save(new User(id, username, "user"))).getRoles();
    }
}
