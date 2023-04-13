package ru.otus.mar.testsystem.domain;

import java.util.List;

public record Question(String id, String question, List<String> answerVariants, String correctAnswer) {

}