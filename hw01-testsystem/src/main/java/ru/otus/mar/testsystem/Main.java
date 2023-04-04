package ru.otus.mar.testsystem;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.mar.testsystem.service.TestService;
import ru.otus.mar.testsystem.ui.AppUI;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        TestService testService = context.getBean(TestService.class);
        AppUI appUI = context.getBean(AppUI.class);
        appUI.runTest(testService.getRandomFullTest());

        context.close();
    }
}
