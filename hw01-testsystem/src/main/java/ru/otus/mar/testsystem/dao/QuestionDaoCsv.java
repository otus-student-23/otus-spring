package ru.otus.mar.testsystem.dao;

import ru.otus.mar.testsystem.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuestionDaoCsv implements QuestionDao {

    private static final String CSV_COLUMN_SEPARATOR = ";";

    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoCsv(String csvFileName) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(csvFileName), StandardCharsets.UTF_8)
        )) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] values = line.split(CSV_COLUMN_SEPARATOR);
                if (!values[0].isBlank()) {
                    questions.add(new Question(values[0], Arrays.stream(values).skip(1).collect(Collectors.toList())));
                }
            }
        }
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }

    @Override
    public List<Question> findRandomAll() {
        return findRandom(questions.size());
    }

    @Override
    public List<Question> findRandom(int limit) {
        return new Random().ints(0, questions.size())
                .distinct()
                .limit(Math.min(limit, questions.size()))
                .mapToObj(questions::get)
                .collect(Collectors.toList());
    }
}
