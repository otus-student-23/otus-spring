package ru.otus.mar.testsystem.service;

import ru.otus.mar.testsystem.domain.Question;

import java.io.InputStream;
import java.util.List;

public interface QuestionsSerializer {

    List<Question> deserialize(InputStream in);
}
