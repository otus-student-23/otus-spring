package ru.otus.mar.testsystem.ui;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.mar.testsystem.domain.Question;
import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.domain.TestStatus;
import ru.otus.mar.testsystem.domain.User;
import ru.otus.mar.testsystem.service.TestService;

import java.util.Locale;
import java.util.Scanner;
import java.util.stream.IntStream;

import static org.fusesource.jansi.Ansi.ansi;

@ShellComponent
public class AppUIShell implements PromptProvider {

    private final Terminal terminal;

    private final TestService testService;

    private final MessageSource messageSource;

    private final Locale locale;

    private User user;

    private Test test;

    public AppUIShell(Terminal terminal, TestService testService, MessageSource messageSource,
                      @Value("${application.locale:en}") Locale locale) {
        this.terminal = terminal;
        this.testService = testService;
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("testsystem:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

    @ShellMethod(value = "login", key = {"i", "login"})
    public User login(@ShellOption(defaultValue = "unknown") String lastName,
                      @ShellOption(defaultValue = "unknown") String firstName) {
        test = null;
        return user = new User(lastName, firstName);
    }

    @ShellMethod(value = "logout", key = {"o", "logout"})
    public void logout() {
        test = null;
        user = null;
    }

    @ShellMethod(value = "test", key = {"t", "test"})
    @ShellMethodAvailability(value = "isLogined")
    public void test() {
        runTest(test = testService.createTest(user));
    }

    @ShellMethod(value = "retest", key = {"r", "retest"})
    @ShellMethodAvailability(value = "isTestExist")
    public void retest() {
        runTest(test);
    }

    @ShellMethod(value = "print", key = {"p", "print"})
    @ShellMethodAvailability(value = "isTestExist")
    public String print() {
        return testService.printTestResult(test);
    }

    private Availability isLogined() {
        return (user == null)
                ? Availability.unavailable("Please, introduce yourself...")
                : Availability.available();
    }

    private Availability isTestExist() {
        return (test == null)
                ? Availability.unavailable("Please, do test...")
                : Availability.available();
    }

    private void runTest(Test test) {
        var scanner = new Scanner(terminal.input());
        var writer = terminal.writer();
        for (int n = 0; n < test.questions().size(); n++) {
            Question question = test.questions().get(n);
            String askAnswer = String.format("   %s %s: ", localeMessage("enterAnswer"), question.options());
            writer.printf("%s%n%d) %s%n%s", drawProgressBar(n), (n + 1), question.question(), askAnswer).flush();
            while (!test.setAnswer(n, scanner.nextLine())) {
                writer.printf("%s%s", ansi().cursorUp(1).eraseLine(), askAnswer).flush();
            }
            IntStream.range(0, 3).forEach(j -> writer.print(ansi().cursorUp(1).eraseLine()));
        }
        writer.printf("%s%nTest %s%s%s%n",
                drawProgressBar(test.questions().size()),
                test.getStatus().equals(TestStatus.PASSED) ? ansi().fgGreen() : ansi().fgRed(),
                test.getStatus().name(), ansi().fgDefault()).flush();
    }

    private String localeMessage(String code) {
        return localeMessage(code, null);
    }

    private String localeMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, locale);
    }

    private String drawProgressBar(int answerNum) {
        int questionsCount = test.questions().size();
        int progressPartLenght = 20 / questionsCount;
        var completedImage = new String(new char[progressPartLenght]).replace('\0', '=');

        var progressBar = new StringBuilder();
        int score = 0;
        for (int i = 0; i < answerNum; i++) {
            if (test.questions().get(i).answer().equals(test.answers()[i])) {
                score++;
                progressBar.append(ansi().fgGreen());
            } else {
                progressBar.append(ansi().fgRed());
            }
            progressBar.append(completedImage).append(ansi().fgDefault());
        }

        return String.format("[%s%s] %s%d%s/%d",
                progressBar,
                new String(new char[20 - progressPartLenght * answerNum]).replace('\0', '-'),
                ansi().fgGreen(), score, ansi().fgDefault(), questionsCount);
    }
}
