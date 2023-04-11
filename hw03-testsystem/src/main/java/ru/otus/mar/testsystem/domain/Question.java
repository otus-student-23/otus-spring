package ru.otus.mar.testsystem.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class Question {

    private final String id;

    private final String question;

    private final List<String> answerVariants;

    private final String correctAnswer;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Question q = (Question) object;
        return id.equals(q.id) && question.equals(q.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question);
    }
}