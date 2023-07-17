package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Жанр")
public class GenreDto {

    private UUID id;

    @Schema(description = "Наименование")
    @NotBlank
    private String name;

    public GenreDto(String name) {
        this(null, name);
    }
}