package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Комментарий к книге")
public class BookCommentDto {

    private UUID id;

    @Schema(description = "Книга")
    @NotBlank
    private BookDto book;

    @Schema(description = "Дата и время создания")
    private LocalDateTime createDate;

    @Schema(description = "Пользователь")
    private String username;

    @Schema(description = "Комментарий")
    @NotBlank
    private String comment;

    public BookCommentDto(BookDto book, LocalDateTime createDate, String user, String comment) {
        this(null, book, createDate, user, comment);
    }
}
