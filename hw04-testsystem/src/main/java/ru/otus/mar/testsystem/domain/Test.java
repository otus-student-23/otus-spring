package ru.otus.mar.testsystem.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record Test(User user, List<Question> questions, String[] answers, int passingScore) {

    public Test(User user, List<Question> questions, int passingScore) {
        this(user, questions, new String[questions.size()], passingScore);
    }

    @Override
    public String[] answers() {
        return answers.clone();
    }

    public int getScore() {
        return (int) IntStream.range(0, questions.size())
                .filter(i -> questions.get(i).answer().equals(answers[i]))
                .count();
    }

    public TestStatus getStatus() {
        int answersCount = (int) Arrays.stream(answers).filter(Objects::nonNull).count();

        if (answersCount == 0) {
            return TestStatus.NEW;
        } else if (answersCount < questions.size()) {
            return TestStatus.IN_PROGRESS;
        } else {
            return (getScore() >= passingScore) ? TestStatus.PASSED : TestStatus.FAILED;
        }
    }

    public boolean setAnswer(int questionNum, String value) {
        if (questions.get(questionNum).options().contains(value)) {
            answers[questionNum] = value;
            return true;
        }
        return false;
    }
}