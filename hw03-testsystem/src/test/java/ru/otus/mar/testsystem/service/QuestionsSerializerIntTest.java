package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.mar.testsystem.QuestionAbstractTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuestionsSerializerIntTest extends QuestionAbstractTest {

    private QuestionsSerializer serializer;

    @BeforeEach
    void init() {
        serializer = new QuestionsSerializerCsv("questions.csv");
    }

    @Test
    void deserialize() { assertArrayEquals(ALL_QUESTIONS.toArray(), serializer.deserialize().toArray()); }
}
