package ru.otus.mar.gatewayserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.Map;

@RestController
public class GatewayController {

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private URL issuerUri;

    @GetMapping("/gateway/keycloak")
    public Mono<Map<String, String>> keycloak() {
        return Mono.just(Map.of(
                "url", String.format("%s://%s", issuerUri.getProtocol(), issuerUri.getAuthority()),
                "realm", issuerUri.getPath().split("/")[2],
                "clientId", "gateway-server"
        ));
    }

    @GetMapping("/gateway/logout")
    public Mono<Void> logout(WebSession session) {
        return session.invalidate();
    }
}