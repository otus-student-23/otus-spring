package ru.otus.mar.testsystem.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.utils.FileReaderImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDaoIntTest {

    private static final List<String> ALL_QUESTIONS = Stream.of(2, 4, 6, 8)
            .map(i -> String.format("Сalculate %d * %d = ?", i, i)).toList();

    private static QuestionDao dao;

    @BeforeAll
    static void init() {
        dao = new QuestionDaoCsv("questions.csv", new FileReaderImpl());
    }

    @Test
    void findAll() {
        assertArrayEquals(ALL_QUESTIONS.toArray(), dao.findAll().stream().map(Question::getQuestion).toArray());
    }

    @Test
    void findRandomAll() {
        assertTrue(ALL_QUESTIONS.containsAll(dao.findRandomAll().stream().map(Question::getQuestion).toList()));
    }

    @Test
    void findRandom() {
        List<Question> questions = dao.findRandom(2);
        assertEquals(2, questions.stream().map(Question::getQuestion).collect(Collectors.toSet()).size());
        assertTrue(ALL_QUESTIONS.containsAll(questions.stream().map(Question::getQuestion).toList()));
    }
}
