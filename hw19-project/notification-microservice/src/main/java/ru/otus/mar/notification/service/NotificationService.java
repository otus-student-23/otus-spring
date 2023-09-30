package ru.otus.mar.notification.service;

import ru.otus.mar.notification.dto.NotificationDto;

public interface NotificationService {

    void publish(NotificationDto notification);
}
