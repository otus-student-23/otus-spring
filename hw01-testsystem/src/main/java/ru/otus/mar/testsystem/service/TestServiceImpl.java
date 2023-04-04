package ru.otus.mar.testsystem.service;

import lombok.RequiredArgsConstructor;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.Test;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    @Override
    public Test getFullTest() {
        return new Test(questionDao.findAll());
    }

    @Override
    public Test getRandomFullTest() {
        return new Test(questionDao.findRandomAll());
    }

    @Override
    public Test getRandomTest(int limitQuestions) {
        return new Test(questionDao.findRandom(limitQuestions));
    }
}
