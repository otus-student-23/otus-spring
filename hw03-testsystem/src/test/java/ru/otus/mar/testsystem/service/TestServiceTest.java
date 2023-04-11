package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import ru.otus.mar.testsystem.QuestionAbstractTest;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.TestType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestServiceTest extends QuestionAbstractTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private MessageSource messageSource;

    private TestService service;

    @BeforeEach
    void init() {
        questionDao = mock(QuestionDao.class);
        service = new TestServiceImpl(questionDao, messageSource);
    }

    @Test
    void getFullTest() {
        given(questionDao.findAll()).willReturn(ALL_QUESTIONS);
        assertArrayEquals(ALL_QUESTIONS.toArray(), service.createTest(TestType.FULL).getQuestions().toArray());
    }

    @Test
    void getRandomFullTest() {
        given(questionDao.findRandomAll()).willReturn(ALL_QUESTIONS);
        var test = service.createTest(TestType.RANDOM_FULL);
        verify(questionDao).findRandomAll();
        assertTrue(ALL_QUESTIONS.containsAll(test.getQuestions()));
    }
}
