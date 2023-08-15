package ru.otus.mar.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Main: http://localhost:8080/");
        System.out.println("Actuator: http://localhost:8080/swagger-ui/index.html#/Actuator");
        System.out.println("Hateoas: http://localhost:8080/hateoas");
    }
}
