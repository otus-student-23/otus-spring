package ru.otus.mar.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Пользователь")
public class UserDto {

    private UUID id;

    @Schema(description = "Имя пользователя")
    @NotBlank
    private String username;

    @Schema(description = "Роли")
    @NotBlank
    private String roles;
}