package ru.otus.mar.booklibrary.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class AsyncServiceImpl implements AsyncService {

    @Async
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
