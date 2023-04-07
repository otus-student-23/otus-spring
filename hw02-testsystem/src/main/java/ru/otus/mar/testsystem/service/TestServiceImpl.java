package ru.otus.mar.testsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.Test;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    @Value("${passingScore}")
    private int passingScore;

    @Override
    public Test getFullTest() {
        return new Test(questionDao.findAll(), passingScore);
    }

    @Override
    public Test getRandomFullTest() {
        return new Test(questionDao.findRandomAll(), passingScore);
    }

    @Override
    public Test getRandomTest(int limitQuestions) {
        return new Test(questionDao.findRandom(limitQuestions), passingScore);
    }

    public String printTestResult(Test test) {
        return new StringBuilder()
                .append("Status: ").append(test.getStatus().name()).append('\n')
                .append("User: ").append(test.getUser().getLastName()).append(" ")
                .append(test.getUser().getFirstName()).append('\n')
                .append("Questions: ").append(test.getQuestions().size()).append('\n')
                .append("Answers: ").append(Arrays.toString(test.getAnswers())).append('\n')
                .append("Correct answers: ").append(test.getCorrectAnswersCount())
                .toString();
    }
}
