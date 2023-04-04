package ru.otus.mar.testsystem.domain;

import java.util.Arrays;
import java.util.List;

public class Test {

    private final User user;

    private TestStatus status;

    private final List<Question> questions;

    private final String[] answers;

    public Test(List<Question> questions) {
        this.user = new User();
        this.questions = questions;
        this.answers = new String[questions.size()];
        this.status = TestStatus.NEW;
    }

    public String printResult() {
        return new StringBuilder()
                .append("Status: ").append(status.name()).append('\n')
                .append("User: ").append(user.getLastName()).append(" ").append(user.getFirstName()).append('\n')
                .append("Questions: ").append(questions.size()).append('\n')
                .append("Answers: ").append(Arrays.toString(answers))
                .toString();
    }

    public User getUser() {
        return user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public boolean setAnswer(int questionNumber, String value) {
        if (questions.get(questionNumber).getAnswerVariants().contains(value)) {
            answers[questionNumber] = value;
            Arrays.stream(answers).filter(a -> a != null).count();
            status = (Arrays.stream(answers).filter(a -> a != null).count() == questions.size())
                    ? TestStatus.DONE : TestStatus.IN_PROGRESS;
            return true;
        }
        return false;
    }
}
