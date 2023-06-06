package ru.otus.mar.booklibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCommentDto {

    private UUID id;

    private BookDto book;

    private String comment;

    public BookCommentDto(BookDto book, String comment) {
        this(null, book, comment);
    }
}
