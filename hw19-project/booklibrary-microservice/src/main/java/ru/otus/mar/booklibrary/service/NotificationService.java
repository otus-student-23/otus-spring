package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.otus.mar.notification.client.NotificationClient;
import ru.otus.mar.notification.client.NotificationDto;

import java.sql.Connection;
import java.util.stream.Stream;

/**
 * https://www.baeldung.com/spring-postgresql-message-broker
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationService implements CommandLineRunner {

    private final NotificationClient client;

    private final JdbcTemplate jdbc;

    private final AsyncService async;

    @Override
    public void run(String... args) {
        Stream.of("author", "genre", "book", "book_comment").forEach(channel ->
                async.execute(() -> listen(channel))
        );
    }

    private void listen(String channel) {
        jdbc.execute((Connection c) -> {
            c.createStatement().execute("LISTEN " + channel + "_events");
            PGConnection pgconn = c.unwrap(PGConnection.class);
            while (!Thread.currentThread().isInterrupted()) {
                PGNotification[] nts = pgconn.getNotifications(10000);
                if (nts == null || nts.length == 0) {
                    continue;
                }
                for (PGNotification nt: nts) {
                    log.debug("Notice: {} {}", channel, nt.getParameter());
                    client.publishSilently(new NotificationDto("/topic/" + channel, nt.getParameter()));
                }
            }
            return 0;
        });
    }
}
