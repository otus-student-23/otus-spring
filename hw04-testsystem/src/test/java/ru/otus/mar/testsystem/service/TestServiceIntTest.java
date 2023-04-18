package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.mar.testsystem.AbstractTest;
import ru.otus.mar.testsystem.dao.QuestionDao;
import ru.otus.mar.testsystem.domain.TestType;
import ru.otus.mar.testsystem.domain.User;

import static org.junit.jupiter.api.Assertions.*;

public class TestServiceIntTest extends AbstractTest {

    @Autowired
    private TestService service;

    @Autowired
    private QuestionDao questionDao;

    private final User user = new User(null, null);

    @Test
    void getFullTest() {
        assertArrayEquals(ALL_QUESTIONS_RUS.toArray(), service.createTest(user, TestType.FULL).questions().toArray());
    }

    @Test
    void getRandomFullTest() {
        var test = service.createTest(user, TestType.RANDOM_FULL);
        assertTrue(ALL_QUESTIONS_RUS.containsAll(test.questions()));
        assertTrue(test.questions().containsAll(ALL_QUESTIONS_RUS));
    }
}
