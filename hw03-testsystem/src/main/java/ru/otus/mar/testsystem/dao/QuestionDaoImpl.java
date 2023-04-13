package ru.otus.mar.testsystem.dao;

import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.service.QuestionsLocalizer;
import ru.otus.mar.testsystem.service.QuestionsSerializer;

import java.util.List;
import java.util.Random;

@Service
public class QuestionDaoImpl implements QuestionDao {

    private final QuestionsSerializer serializer;

    private final QuestionsLocalizer localizer;

    private List<Question> questions;

    public QuestionDaoImpl(QuestionsSerializer serializer, QuestionsLocalizer localizer) {
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
        return questions == null ? questions = localizer.localize(serializer.deserialize()) : questions;
    }
}
