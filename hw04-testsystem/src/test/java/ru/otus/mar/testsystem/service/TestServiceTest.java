package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.testsystem.AbstractTest;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.TestType;
import ru.otus.mar.testsystem.domain.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class TestServiceTest extends AbstractTest {

    @Autowired
    private TestService service;

    @MockBean
    private QuestionDao questionDao;

    private final User user = new User(null, null);

    @Test
    void getRandomFullTest() {
        given(questionDao.findRandomAll()).willReturn(ALL_QUESTIONS);
        var test = service.createTest(user, TestType.RANDOM_FULL);
        verify(questionDao).findRandomAll();
        assertTrue(ALL_QUESTIONS.containsAll(test.questions()));
        assertTrue(test.questions().containsAll(ALL_QUESTIONS));
    }
}
