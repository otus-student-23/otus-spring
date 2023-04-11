package ru.otus.mar.testsystem.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.service.QuestionsLocalizer;
import ru.otus.mar.testsystem.service.QuestionsSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

@Service
public class QuestionDaoResource implements QuestionDao {

    private final String fileName;

    private final QuestionsSerializer serializer;

    private final QuestionsLocalizer localizer;

    private List<Question> questions;

    public QuestionDaoResource(@Value("${application.questionsFile}") String fileName,
                               QuestionsSerializer serializer, QuestionsLocalizer localizer) {
        this.fileName = fileName;
        this.serializer = serializer;
        this.localizer = localizer;
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
                .toList();
    }

    private List<Question> getQuestions() {
        if (questions == null) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
                questions = localizer.localize(serializer.deserialize(in));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return questions;
    }
}
