package ru.otus.mar.booklibrary.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentsLiveHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbc;

    @Override
    public Health health() {
        try {
            if (!jdbc.queryForObject(
                    "select exists(select 1 from book_comment where create_date > current_timestamp() - 1)",
                    Boolean.class
            )) {
                throw new RuntimeException("Ни одного нового комментария к книгам за последние сутки");
            }
            return Health.status(Status.UP).build();
        } catch (Exception e) {
            return Health.status(Status.DOWN)
                    .withDetail("detail", e.getMessage())
                    .build();
        }
    }
}
