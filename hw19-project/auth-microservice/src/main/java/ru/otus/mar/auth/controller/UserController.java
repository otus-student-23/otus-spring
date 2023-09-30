package ru.otus.mar.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mar.auth.dto.UserDto;
import ru.otus.mar.auth.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Пользователи")
@PreAuthorize("hasAuthority('admin')")
public class UserController {

    private final UserService service;

    @GetMapping("/api/auth/user")
    @Operation(summary = "Список")
    public List<UserDto> list() {
        return service.getAll();
    }

    @GetMapping("/api/auth/user/{id}")
    @Operation(summary = "Получить")
    public UserDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping("/api/auth/user")
    @Operation(summary = "Добавить")
    public UserDto create(@RequestBody UserDto user) {
        user.setId(null);
        return service.create(user);
    }

    @PutMapping("/api/auth/user/{id}")
    @Operation(summary = "Изменить")
    public UserDto update(@PathVariable UUID id, @RequestBody UserDto user) {
        user.setId(id);
        return service.update(user);
    }

    @DeleteMapping("/api/auth/user/{id}")
    @Operation(summary = "Удалить")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
