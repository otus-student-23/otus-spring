package ru.otus.mar.booklibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenreDto {

    private UUID id;

    private String name;

    public GenreDto(String name) {
        this(null, name);
    }
}