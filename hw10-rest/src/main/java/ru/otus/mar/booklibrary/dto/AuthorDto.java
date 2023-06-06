package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Автор")
public class AuthorDto {

    private UUID id;

    @Schema(description = "Наименование")
    @NotBlank
    @Size(min = 1, max = 100)//TODO
    private String name;

    public AuthorDto(String name) {
        this(null, name);
    }
}