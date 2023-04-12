package ru.otus.mar.testsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.domain.TestType;

import java.util.Arrays;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    @Value("${application.testType:RANDOM_FULL}")
    private TestType testType;

    @Value("${application.passingScore}")
    private int passingScore;

    private final MessageSource messageSource;

    @Value("${application.locale:en}")
    private Locale locale;

    @Override
    public Test createTest() {
        return createTest(testType);
    }

    @Override
    public Test createTest(TestType testType) {
        return new Test(
                switch (testType) {
                    case FULL -> questionDao.findAll();
                    case RANDOM_FULL -> questionDao.findRandomAll();
                    },
                passingScore);
    }

    public String printTestResult(Test test) {
        return messageSource.getMessage(
                "resultReport",
                new String[]{test.getStatus().name(),
                        test.getUser().lastName(), test.getUser().firstName(),
                        Arrays.toString(test.getAnswers()),
                        String.valueOf(test.getCorrectAnswersCount()), String.valueOf(test.getQuestions().size())},
                locale);
    }
}
