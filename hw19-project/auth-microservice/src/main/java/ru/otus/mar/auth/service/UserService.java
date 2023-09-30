package ru.otus.mar.auth.service;

import ru.otus.mar.auth.dto.UserDto;

import java.util.UUID;

public interface UserService extends AbstractCrudService<UserDto, UUID> {

    String getRoles(UUID id, String username);
}
