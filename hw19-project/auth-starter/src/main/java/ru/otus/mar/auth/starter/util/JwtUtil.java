package ru.otus.mar.auth.starter.util;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

public class JwtUtil {

    public static String getUsername(JwtAuthenticationToken jwt) {
        return (String) jwt.getTokenAttributes().get(PREFERRED_USERNAME);
    }
}
