package ru.otus.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.otus.spring.integration.services.ButterflyLifecycleService;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

		ButterflyLifecycleService service = ctx.getBean(ButterflyLifecycleService.class);
		service.birthBaterfly("urticae");
	}
}
