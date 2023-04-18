package ru.otus.mar.testsystem;

import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.mar.testsystem.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public abstract class AbstractTest {

    protected final List<Question> ALL_QUESTIONS = new ArrayList<>();

    protected final List<Question> ALL_QUESTIONS_RUS = new ArrayList<>();

    protected final String ALL_QUESTIONS_CSV;

    public AbstractTest() {
        var sb = new StringBuilder();
        Stream.of(2, 4, 6, 8)
                .map(i -> String.format("Calculate %d * %d = ?;%d;0;1;%d;%d;%d;%d;", i, i, i * i, i, i + i, i * i, i * 10 + i))
                .forEach(row -> {
                    String[] cols = row.split(";");
                    ALL_QUESTIONS.add(new Question(cols[0], Arrays.stream(cols).skip(2).toList(), cols[1]));
                    ALL_QUESTIONS_RUS.add(new Question(cols[0].replaceFirst("^Calculate", "Вычислите"),
                            Arrays.stream(cols).skip(2).toList(), cols[1]));
                    sb.append(row).append('\n');
                });
        ALL_QUESTIONS_CSV = sb.toString();
    }
}
