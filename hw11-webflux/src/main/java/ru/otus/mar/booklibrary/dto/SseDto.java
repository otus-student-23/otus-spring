package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Событие")
public record SseDto<E>(String event, E entity) {

}
