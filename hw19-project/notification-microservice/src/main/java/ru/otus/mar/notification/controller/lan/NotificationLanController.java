package ru.otus.mar.notification.controller.lan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mar.notification.dto.NotificationDto;
import ru.otus.mar.notification.service.NotificationService;

@RestController
@RequiredArgsConstructor
public class NotificationLanController {

    private final NotificationService service;

    @PostMapping(path = "/lan/notification/publish")
    @ResponseStatus(code = HttpStatus.OK)
    private void publish(@RequestBody NotificationDto notification) {
        service.publish(notification);
    }
}
