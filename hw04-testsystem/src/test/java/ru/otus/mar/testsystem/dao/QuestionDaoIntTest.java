package ru.otus.mar.testsystem.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.mar.testsystem.AbstractTest;
import ru.otus.mar.testsystem.domain.Question;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDaoIntTest extends AbstractTest {

    @Autowired
    private QuestionDao dao;

    @Test
    void findAll() {
        assertArrayEquals(ALL_QUESTIONS_RUS.toArray(), dao.findAll().toArray());
    }

    @Test
    void findRandomAll() {
        assertTrue(dao.findRandomAll().containsAll(ALL_QUESTIONS_RUS));
    }

    @Test
    void findRandom() {
        List<Question> questions = dao.findRandom(2);
        assertEquals(2, new HashSet<>(questions).size());
        assertTrue(ALL_QUESTIONS_RUS.containsAll(questions));
    }
}
