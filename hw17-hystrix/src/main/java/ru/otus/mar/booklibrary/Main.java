package ru.otus.mar.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Main: http://localhost:8080/");
        System.out.println(
                "Hystrix: http://localhost:8080/hystrix/monitor?stream=http://localhost:8080/actuator/hystrix.stream"
        );
    }
}
