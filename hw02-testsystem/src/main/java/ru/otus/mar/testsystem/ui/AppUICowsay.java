package ru.otus.mar.testsystem.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.service.TestService;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.out;

@Component
@RequiredArgsConstructor
public class AppUICowsay implements AppUI {

    private static final String LINE_SEPERATOR = String.format("%25s%n", "").replace(' ', '-');

    private static final String COW_IMAGE = """
               \\   ^__^
                \\  (oo)\\_______
                   (__)\\       )\\/\\
                       ||----w |
                       ||     ||
            """;

    private final TestService testService;

    @Override
    public void runTest() {
        Test test = testService.getRandomFullTest();
        out.printf("%nWelcome to TestSystem. Please, introduce yourself...%n%s", LINE_SEPERATOR);
        out.println(COW_IMAGE);
        out.print("lastname: ");
        Scanner s = new Scanner(System.in);
        test.getUser().setLastName(s.next());
        out.print("firstname: ");
        test.getUser().setFirstName(s.next());
        out.printf("%nTest started at %tT%n%s", new Date(), LINE_SEPERATOR);
        int i = 1;
        for (Question question : test.getQuestions()) {
            out.printf("%d. %s%n", i++, question.getQuestion());
            do {
                out.printf("    Enter one value from %s: ", Arrays.toString(question.getAnswerVariants().toArray()));
            } while (!test.setAnswer((i - 2), s.next()));
        }
        out.printf("%sTest completed at %tT%n", LINE_SEPERATOR, new Date());
        out.println(testService.printTestResult(test));
    }
}
