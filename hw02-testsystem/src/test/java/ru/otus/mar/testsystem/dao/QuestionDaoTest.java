package ru.otus.mar.testsystem.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuestionDaoTest {

    private static final List<Question> ALL_QUESTIONS = new ArrayList<>();

    private static QuestionDao dao;

    @Mock
    private static FileReader fileReader;

    @BeforeAll
    static void init() {
        List<String> csv_question_list = Stream.of(2, 4, 6, 8)
                .map(i -> String.format("Сalculate %d * %d = ?;%d;0;1;%d;%d;%d;", i, i, i * i, i + i, i * i, i * 10 + i))
                .toList();
        fileReader = mock(FileReader.class);
        given(fileReader.fileToList(anyString())).willReturn(csv_question_list);
        dao = new QuestionDaoCsv(anyString(), fileReader);
        csv_question_list.forEach(s -> {
            String[] values = s.split(";");
            ALL_QUESTIONS.add(new Question(
                    values[0],
                    Arrays.stream(values).skip(2).collect(Collectors.toList()),
                    values[1]
            ));
        });
    }

    @Test
    void findAll() {
        assertArrayEquals(
                ALL_QUESTIONS.stream().map(Question::getQuestion).toArray(),
                dao.findAll().stream().map(Question::getQuestion).toArray()
        );
    }

    @Test
    void findRandomAll() {
        assertTrue(
                ALL_QUESTIONS.stream().map(Question::getQuestion).toList().containsAll(
                        dao.findRandomAll().stream().map(Question::getQuestion).toList()
                )
        );
    }

    @Test
    void findRandom() {
        List<Question> questions = dao.findRandom(2);
        assertEquals(2, questions.stream().map(Question::getQuestion).collect(Collectors.toSet()).size());
        assertTrue(
                ALL_QUESTIONS.stream().map(Question::getQuestion).toList().containsAll(
                        questions.stream().map(Question::getQuestion).toList()
                )
        );
    }
}
