package ru.otus.mar.auth.controller.lan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mar.auth.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Пользователи")
public class UserLanController {

    private final UserService service;

    @GetMapping("/lan/auth/user/{id}/roles")
    @Operation(summary = "Роли пользователя")
    public String getRoles(@PathVariable UUID id, @RequestParam String username) {
        return service.getRoles(id, username);
    }
}
