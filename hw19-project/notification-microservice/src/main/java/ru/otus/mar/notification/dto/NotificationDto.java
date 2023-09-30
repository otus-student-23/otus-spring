package ru.otus.mar.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationDto {

    private String channel;

    private Object payload;
}
