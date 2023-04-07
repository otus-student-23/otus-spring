package ru.otus.mar.testsystem.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
public class QuestionDaoCsv implements QuestionDao {

    private static final String CSV_COLUMN_SEPARATOR = ";";

    private final String csvFileName;

    private final FileReader fileReader;

    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoCsv(@Value("${csvFileName}") String csvFileName, FileReader fileReader) {
        this.csvFileName = csvFileName;
        this.fileReader = fileReader;
    }

    private void importFromCsv() {
        fileReader.fileToList(csvFileName).forEach(s -> {
            String[] values = s.split(CSV_COLUMN_SEPARATOR);
            if (!values[0].isBlank()) {
                questions.add(new Question(
                        values[0],
                        Arrays.stream(values).skip(2).collect(Collectors.toList()),
                        values[1]
                ));
            }
        });
    }

    private List<Question> getQuestions() {
        if (questions.isEmpty()) {
            importFromCsv();
        }
        return questions;
    }

    @Override
    public List<Question> findAll() {
        return getQuestions();
    }

    @Override
    public List<Question> findRandomAll() {
        return findRandom(getQuestions().size());
    }

    @Override
    public List<Question> findRandom(int limit) {
        var questions = getQuestions();
        return new Random().ints(0, questions.size())
                .distinct()
                .limit(Math.min(limit, questions.size()))
                .mapToObj(questions::get)
                .collect(Collectors.toList());
    }
}
