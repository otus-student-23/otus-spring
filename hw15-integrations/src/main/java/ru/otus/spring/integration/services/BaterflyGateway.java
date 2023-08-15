package ru.otus.spring.integration.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.model.Egg;

@MessagingGateway
public interface BaterflyGateway {

    @Gateway(requestChannel = "eggChannel")
    void birthBaterfly(Egg egg);
}
