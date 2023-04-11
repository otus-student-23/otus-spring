package ru.otus.mar.testsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.mar.testsystem.QuestionAbstractTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionsLocalizerTest extends QuestionAbstractTest {

    private QuestionsLocalizer localizer;

    @BeforeEach
    void init() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/appmessages");
        messageSource.setDefaultEncoding("UTF-8");

        localizer = new QuestionsLocalizerMessageSource(messageSource, "ru-RU");
    }

    @Test
    void localize() {
        assertEquals("Вычислите 2 * 2 = ?", localizer.localize(ALL_QUESTIONS).get(0).getQuestion());
    }}
