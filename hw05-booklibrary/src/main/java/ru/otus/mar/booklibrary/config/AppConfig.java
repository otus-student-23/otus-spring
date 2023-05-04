package ru.otus.mar.booklibrary.config;

import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.mar.booklibrary.service.odata.ExpressionVisitorSql92;

@Configuration
public class AppConfig {

    @Bean
    public ExpressionVisitor expressionVisitor() {
        return new ExpressionVisitorSql92();
    }
}
