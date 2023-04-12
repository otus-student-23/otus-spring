package ru.otus.mar.testsystem.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Test {

    private User user;

    private TestStatus status;

    private final List<Question> questions;

    private final String[] answers;

    private int correctAnswersCount = 0;

    private final int passingScore;

    public Test(List<Question> questions, int passingScore) {
        this.questions = questions;
        this.answers = new String[questions.size()];
        this.status = TestStatus.NEW;
        this.passingScore = passingScore;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public TestStatus getStatus() {
        return status;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return Arrays.copyOf(answers, answers.length);
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    private void updateCorrectAnswersCount() {
        correctAnswersCount = (int) IntStream.range(0, questions.size())
                .filter(i -> questions.get(i).correctAnswer().equals(answers[i]))
                .count();
    }

    private void updateStatus() {
        int answersCount = (int) Arrays.stream(answers).filter(Objects::nonNull).count();
        int questionsCount = questions.size();

        if (answersCount == 0) {
            status = TestStatus.NEW;
        } else if (answersCount < questionsCount) {
            status = TestStatus.IN_PROGRESS;
        } else {
            if (correctAnswersCount >= passingScore) {
                status = TestStatus.PASSED;
            } else {
                status = TestStatus.FAILED;
            }
        }
    }

    public boolean setAnswer(int questionNumber, String value) {
        if (questions.get(questionNumber).answerVariants().contains(value)) {
            answers[questionNumber] = value;
            updateCorrectAnswersCount();
            updateStatus();
            return true;
        }
        return false;
    }
}
