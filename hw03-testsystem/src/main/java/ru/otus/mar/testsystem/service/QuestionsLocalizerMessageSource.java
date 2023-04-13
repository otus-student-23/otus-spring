package ru.otus.mar.testsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class QuestionsLocalizerMessageSource implements QuestionsLocalizer {

    private final MessageSource messageSource;

    private final Locale locale;

    public QuestionsLocalizerMessageSource(MessageSource messageSource,
                                           @Value("${application.locale:en}") String locale) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public List<Question> localize(List<Question> question) {
        List<Question> result = new ArrayList<>(question.size());
        question.forEach(q -> result.add(new Question(
                q.id(),
                messageSource.getMessage(String.format("question.%s", q.id()), null, q.question(), locale),
                q.answerVariants(),
                q.correctAnswer()
        )));
        return result;
    }
}
