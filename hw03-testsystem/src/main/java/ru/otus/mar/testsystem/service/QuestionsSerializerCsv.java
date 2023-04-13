package ru.otus.mar.testsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionsSerializerCsv implements QuestionsSerializer {

    private static final String CSV_COLUMN_SEPARATOR = ";";

    private final String fileName;

    public QuestionsSerializerCsv(@Value("${application.questionsFile}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> deserialize() {
        List<Question> questions = new ArrayList<>();
        try (var reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] cols = row.split(CSV_COLUMN_SEPARATOR);
                if (!cols[0].isBlank()) {
                    questions.add(new Question(cols[0], cols[1], Arrays.stream(cols).skip(3).toList(), cols[2]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
}
