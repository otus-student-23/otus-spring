package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.Question;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestServiceTest {

    private static final List<Question> QUESTIONS = Stream.of(2, 4, 6, 8)
            .map(i -> new Question(String.format("Сalculate %d * %d = ?", i, i), null))
            .toList();

    @Mock
    private static QuestionDao questionDao;

    private static TestService service;

    @BeforeAll
    static void init() {
        questionDao = mock(QuestionDao.class);
        service = new TestServiceImpl(questionDao);
    }

    @Test
    void getFullTest() {
        given(questionDao.findAll()).willReturn(QUESTIONS);
        assertArrayEquals(
                QUESTIONS.stream().map(Question::getQuestion).toArray(),
                service.getFullTest().getQuestions().stream().map(Question::getQuestion).toArray()
        );
    }

    @Test
    void getRandomFullTest() {
        given(questionDao.findRandomAll()).willReturn(QUESTIONS);
        service.getRandomFullTest();
        verify(questionDao).findRandomAll();
    }

    @Test
    void getRandomTest() {
        given(questionDao.findRandom(anyInt())).willReturn(QUESTIONS);
        service.getRandomTest(2);
        verify(questionDao).findRandom(2);
    }
}
