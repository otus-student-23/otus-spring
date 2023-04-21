package ru.otus.mar.testsystem.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.testsystem.AbstractTest;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.exception.QuestionsDeserializeException;
import ru.otus.mar.testsystem.service.QuestionsSerializer;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class QuestionDaoTest extends AbstractTest {

    @Autowired
    private QuestionDao dao;

    @MockBean
    private QuestionsSerializer serializer;

    @Test
    void findAll() {
        doReturn(ALL_QUESTIONS).when(serializer).deserialize();
        assertArrayEquals(ALL_QUESTIONS.toArray(), dao.findAll().toArray());
    }

    @Test
    void findRandomAll() {
        when(serializer.deserialize()).thenReturn(ALL_QUESTIONS);
        assertTrue(dao.findRandomAll().containsAll(ALL_QUESTIONS));
    }

    @Test
    void findRandom() {
        given(serializer.deserialize()).willReturn(ALL_QUESTIONS);
        List<Question> questions = dao.findRandom(2);
        assertEquals(2, new HashSet<>(questions).size());
        assertTrue(ALL_QUESTIONS.containsAll(questions));
    }

    @Test
    void findAllException() {
        doThrow(QuestionsDeserializeException.class).when(serializer).deserialize();
        assertThrows(QuestionsDeserializeException.class, dao::findAll);
    }

    @Test
    void findRandomAllException() {
        when(serializer.deserialize()).thenThrow(QuestionsDeserializeException.class);
        assertThrows(QuestionsDeserializeException.class, dao::findRandomAll);
    }
}
