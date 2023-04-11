package ru.otus.mar.testsystem.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.mar.testsystem.QuestionAbstractTest;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.service.QuestionsLocalizerMessageSource;
import ru.otus.mar.testsystem.service.QuestionsSerializerCsv;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDaoIntTest extends QuestionAbstractTest {

    private QuestionDao dao;

    @BeforeEach
    void init() {
        dao = new QuestionDaoImpl(new QuestionsSerializerCsv("questions.csv"),
                new QuestionsLocalizerMessageSource(new ResourceBundleMessageSource(), "ru-RU"));
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
