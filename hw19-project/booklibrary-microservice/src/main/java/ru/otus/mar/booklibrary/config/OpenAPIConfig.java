package ru.otus.mar.booklibrary.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://keepgrowing.in/java/springboot/keycloak-with-spring-boot-4-simple-guide-for-roles-and-authorities/
 */
@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class OpenAPIConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    OpenAPI openApi() {
        return new OpenAPI().components(
                new Components().addSecuritySchemes("openId", new SecurityScheme()
                        .type(SecurityScheme.Type.OPENIDCONNECT)
                        .openIdConnectUrl(issuerUri + "/.well-known/openid-configuration")))
                .addSecurityItem(new SecurityRequirement().addList("openId"));
    }
}
