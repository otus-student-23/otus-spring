package ru.otus.mar.testsystem.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.domain.User;
import ru.otus.mar.testsystem.service.TestService;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.out;

@Component
@RequiredArgsConstructor
public class AppUICowsay implements AppUI, CommandLineRunner {

    private static final String LINE_SEPERATOR = String.format("%25s%n", "").replace(' ', '-');

    private static final String COW_IMAGE = """
               \\   ^__^
                \\  (oo)\\_______
                   (__)\\       )\\/\\
                       ||----w |
                       ||     ||
            """;

    private final TestService testService;

    private final MessageSource messageSource;

    @Value("${application.locale:en}")
    private Locale locale;

    @Override
    public void runTest() {
        Test test = testService.createTest();
        out.printf("%n%s%n%s%s%n", localeMessage("welcome", new String[]{"TestSystem"}), LINE_SEPERATOR, COW_IMAGE);
        Scanner scanner = new Scanner(System.in);
        out.printf("%s: ", localeMessage("lastname"));
        String lastName = scanner.next();
        out.printf("%s: ", localeMessage("firstname"));
        test.setUser(new User(lastName, scanner.next()));
        out.printf("%n%s %tT%n%s", localeMessage("startedAt"), new Date(), LINE_SEPERATOR);
        int i = 1;
        for (Question question : test.getQuestions()) {
            out.printf("%d. %s%n", i++, question.question());
            do {
                out.printf("    %s %s: ", localeMessage("enterAnswer"),
                        Arrays.toString(question.answerVariants().toArray()));
            } while (!test.setAnswer((i - 2), scanner.next()));
        }
        out.printf("%s%s %tT%n", LINE_SEPERATOR, localeMessage("completedAt"), new Date());
        out.println(testService.printTestResult(test));
    }

    @Override
    public void run(String... args) {
        runTest();
    }

    private String localeMessage(String code) {
        return localeMessage(code, null);
    }

    private String localeMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, locale);
    }
}
