package ru.otus.mar.testsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.mar.testsystem.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@ConditionalOnProperty(prefix = "application.questions.source", name = "type", havingValue = "message-source")
public class QuestionsSerializerMessageSource implements QuestionsSerializer {

    private final MessageSource messageSource;

    private final Locale locale;

    public QuestionsSerializerMessageSource(MessageSource messageSource,
                                            @Value("${application.locale:en}") String locale) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public List<Question> deserialize() {
        List<Question> questions = new ArrayList<>();
        int i = 1;
        String row;
        while ((row = messageSource.getMessage(String.format("question.%d", i++), null, null, locale)) != null) {
            String[] cols = row.split(";");
            questions.add(new Question(cols[0], Arrays.stream(cols).skip(2).toList(), cols[1]));
        }
        return questions;
    }
}
