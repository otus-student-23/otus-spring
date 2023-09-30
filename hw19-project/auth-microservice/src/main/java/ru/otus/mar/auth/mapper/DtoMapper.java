package ru.otus.mar.auth.mapper;

public interface DtoMapper<M, D> {

    D toDto(M model);

    M toEntity(D dto);
}
