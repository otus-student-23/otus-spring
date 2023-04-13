package ru.otus.mar.testsystem.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.mar.testsystem.QuestionAbstractTest;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.service.QuestionsLocalizer;
import ru.otus.mar.testsystem.service.QuestionsSerializer;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuestionDaoTest extends QuestionAbstractTest {

    private QuestionDao dao;

    @Mock
    private QuestionsSerializer serializer;

    @Mock
    private QuestionsLocalizer localizer;

    @BeforeEach
    void init() {
        serializer = mock(QuestionsSerializer.class);
        given(serializer.deserialize()).willReturn(ALL_QUESTIONS);
        localizer = mock(QuestionsLocalizer.class);
        given(localizer.localize(any())).willReturn(ALL_QUESTIONS);
        dao = new QuestionDaoImpl(serializer, localizer);
    }

    @Test
    void findAll() {
        assertArrayEquals(ALL_QUESTIONS.toArray(), dao.findAll().toArray());
    }

    @Test
    void findRandomAll() {
        assertTrue(ALL_QUESTIONS.containsAll(dao.findRandomAll()));
    }

    @Test
    void findRandom() {
        List<Question> questions = dao.findRandom(2);
        assertEquals(2, new HashSet<>(questions).size());
        assertTrue(ALL_QUESTIONS.containsAll(questions));
    }
}
