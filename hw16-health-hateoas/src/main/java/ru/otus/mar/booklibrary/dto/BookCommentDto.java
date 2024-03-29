package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Date createDate;

    @Schema(description = "Комментарий")
    @NotBlank
    private String comment;

    public BookCommentDto(BookDto book, Date createDate, String comment) {
        this(null, book, createDate, comment);
    }
}
