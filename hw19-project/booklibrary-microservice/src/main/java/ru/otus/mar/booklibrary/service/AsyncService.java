package ru.otus.mar.booklibrary.service;

public interface AsyncService {

    void execute(Runnable runnable);
}
