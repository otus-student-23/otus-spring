package ru.otus.mar.testsystem;

import ru.otus.mar.testsystem.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class QuestionAbstractTest {

    protected final List<Question> ALL_QUESTIONS = new ArrayList<>();

    protected final String ALL_QUESTIONS_CSV;

    public QuestionAbstractTest() {
        var sb = new StringBuilder();
        Stream.of(2, 4, 6, 8)
                .map(i -> String.format("Q%d;Сalculate %d * %d = ?;%d;0;1;%d;%d;%d;%d;", i, i, i, i * i, i, i + i, i * i, i * 10 + i))
                .forEach(row -> {
                    String[] cols = row.split(";");
                    ALL_QUESTIONS.add(new Question(cols[0], cols[1], Arrays.stream(cols).skip(3).toList(), cols[2]));
                    sb.append(row).append('\n');
                });
        ALL_QUESTIONS_CSV = sb.toString();
    }
}
