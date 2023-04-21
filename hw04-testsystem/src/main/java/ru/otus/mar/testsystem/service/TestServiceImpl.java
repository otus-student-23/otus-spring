package ru.otus.mar.testsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.domain.TestType;
import ru.otus.mar.testsystem.domain.User;

import java.util.Arrays;
import java.util.Locale;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    private final TestType testType;

    private final int passingScore;

    private final MessageSource messageSource;

    private final Locale locale;

    public TestServiceImpl(
            QuestionDao questionDao,
            @Value("${application.testType:RANDOM_FULL}") TestType testType,
            @Value("${application.passingScore}") int passingScore,
            MessageSource messageSource,
            @Value("${application.locale:en}") String locale
    ) {
        this.questionDao = questionDao;
        this.testType = testType;
        this.passingScore = passingScore;
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public Test createTest(User user) {
        return createTest(user, testType);
    }

    @Override
    public Test createTest(User user, TestType testType) {
        return new Test(
                user,
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
                        test.user().lastName(), test.user().firstName(),
                        Arrays.toString(test.answers()),
                        String.valueOf(test.getScore()), String.valueOf(test.questions().size())},
                locale);
    }
}
