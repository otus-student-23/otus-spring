package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import ru.otus.mar.testsystem.AbstractTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@TestPropertySource(properties = {
        "application.questions.source.type=csv-resource",
        "application.questions.source.file=questions.csv"
})
public class QuestionsSerializerCsvResourceIntTest extends AbstractTest {

    @Autowired
    private QuestionsSerializer serializer;

    @Test
    void deserialize() {
        assertArrayEquals(ALL_QUESTIONS.toArray(), serializer.deserialize().toArray());
    }
}