package ru.otus.mar.testsystem.service;

import ru.otus.mar.testsystem.domain.Test;

public interface TestService {

    Test getFullTest();

    Test getRandomFullTest();

    Test getRandomTest(int limitQuestions);
}
