package ru.otus.mar.booklibrary.service;

import java.util.List;

public interface AbstractCrudService<E, I> {

    E create(E entity);

    E update(E entity);

    void delete(E entity);

    List<E> getAll();
}
