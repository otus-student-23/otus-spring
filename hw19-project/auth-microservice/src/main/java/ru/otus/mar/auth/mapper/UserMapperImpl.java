package ru.otus.mar.auth.mapper;

import org.springframework.stereotype.Service;
import ru.otus.mar.auth.dto.UserDto;
import ru.otus.mar.auth.model.User;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        return user == null ? null : new UserDto(user.getId(), user.getUsername(), user.getRoles());
    }

    @Override
    public User toEntity(UserDto user) {
        return user == null ? null : new User(user.getId(), user.getUsername(), user.getRoles());
    }
}
