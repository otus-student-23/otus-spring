package ru.otus.mar.testsystem;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.mar.testsystem.ui.AppUI;

@ComponentScan
@PropertySource("classpath:application.properties")
@Configuration
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        AppUI appUI = context.getBean(AppUI.class);
        appUI.runTest();

        context.close();
    }
}
