package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.mar.testsystem.AbstractTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuestionsSerializerIntTest extends AbstractTest {

    @Autowired
    private QuestionsSerializer serializer;

    @Test
    void deserialize() {
        assertArrayEquals(ALL_QUESTIONS_RUS.toArray(), serializer.deserialize().toArray());
    }
}