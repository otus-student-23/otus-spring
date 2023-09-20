package ru.otus.mar.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.mar.notification.dto.NotificationDto;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void publish(NotificationDto notification) {
        simpMessagingTemplate.convertAndSend(notification.getChannel(), notification.getPayload());
    }
}
