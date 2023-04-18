package ru.otus.mar.testsystem.dao;

import ru.otus.mar.testsystem.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();

    List<Question> findRandomAll();

    List<Question> findRandom(int limit);
}
