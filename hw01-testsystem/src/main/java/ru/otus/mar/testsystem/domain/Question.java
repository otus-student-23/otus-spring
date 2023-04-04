package ru.otus.mar.testsystem.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Question {

    private final String question;

    private final List<String> answerVariants;
}