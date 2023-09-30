package ru.otus.mar.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-microservice")
public interface NotificationClient {

    @PostMapping("/lan/notification/publish")
    void publish(@RequestBody NotificationDto notification);

    default void publishSilently(NotificationDto notification) {
        try {
            publish(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
