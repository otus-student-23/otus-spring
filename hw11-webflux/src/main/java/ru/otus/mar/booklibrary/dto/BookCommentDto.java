package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Комментарий к книге")
public class BookCommentDto {

    private String id;

    @Schema(description = "Книга")
    @NotBlank
    private BookDto book;

    @Schema(description = "Комментарий")
    @NotBlank
    private String comment;

    public BookCommentDto(BookDto book, String comment) {
        this(null, book, comment);
    }
}
