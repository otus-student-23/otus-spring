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
@Schema(description = "Книга")
public class BookDto {

    private UUID id;

    @Schema(description = "Наименование")
    @NotBlank
    private String name;

    @Schema(description = "Автор")
    @NotBlank
    private AuthorDto author;

    @Schema(description = "Жанр")
    @NotBlank
    private GenreDto genre;

    public BookDto(String name, AuthorDto author, GenreDto genre) {
        this(null, name, author, genre);
    }
}