package ru.otus.mar.testsystem.dao;

import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.service.QuestionsSerializer;

import java.util.List;
import java.util.Random;

@Service
public class QuestionDaoImpl implements QuestionDao {

    private final QuestionsSerializer serializer;

    public QuestionDaoImpl(QuestionsSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public List<Question> findAll() {
        return getQuestions();
    }

    @Override
    public List<Question> findRandomAll() {
        return findRandom(Integer.MAX_VALUE);
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
        return serializer.deserialize();
    }
}
