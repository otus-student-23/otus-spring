package ru.otus.mar.testsystem.service;

import ru.otus.mar.testsystem.domain.Question;

import java.util.List;

public interface QuestionsLocalizer {

    List<Question> localize(List<Question> question);
}
