package ru.otus.mar.testsystem.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@EqualsAndHashCode(of = {"id", "question"})
@RequiredArgsConstructor
@Getter
public class Question {

    private final String id;

    private final String question;

    private final List<String> answerVariants;

    private final String correctAnswer;
}