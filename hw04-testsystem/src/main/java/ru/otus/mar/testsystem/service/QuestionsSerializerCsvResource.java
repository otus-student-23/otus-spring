package ru.otus.mar.testsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.exception.QuestionsDeserializeException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "application.questions.source", name = "type", havingValue = "csv-resource",
        matchIfMissing = true)
public class QuestionsSerializerCsvResource implements QuestionsSerializer {

    private final String fileName;

    public QuestionsSerializerCsvResource(@Value("${application.questions.source.file}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> deserialize() {
        List<Question> questions = new ArrayList<>();
        try (var reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8))) {
            String row;
            while ((row = reader.readLine()) != null) {
                if (!row.isBlank()) {
                    String[] cols = row.split(";");
                    questions.add(new Question(cols[0], Arrays.stream(cols).skip(2).toList(), cols[1]));
                }
            }
        } catch (Exception e) {
            throw new QuestionsDeserializeException("Error load questions from csv resource", e);
        }
        return questions;
    }
}