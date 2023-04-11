package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.Test;
import ru.otus.mar.testsystem.QuestionAbstractTest;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuestionsSerializerTest extends QuestionAbstractTest {

    private final QuestionsSerializer serializer = new QuestionsSerializerCsv();

    @Test
    void deserialize() {
        var in = new ByteArrayInputStream(ALL_QUESTIONS_CSV.getBytes(StandardCharsets.UTF_8));
        assertArrayEquals(ALL_QUESTIONS.toArray(), serializer.deserialize(in).toArray());
    }
}
