package ru.otus.mar.gatewayserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private ReactiveClientRegistrationRepository registrationRepository;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .headers().frameOptions().disable().and()//--- iframe
                .authorizeExchange().pathMatchers("/", "/favicon.ico").permitAll().and()
                .authorizeExchange().pathMatchers("/actuator/**").permitAll().and()//TODO hasIpAddress("prometheus")
                .authorizeExchange().pathMatchers("/swagger-ui/**", "/v3/**", "/api/**").permitAll().and()
                .authorizeExchange().anyExchange().authenticated().and()
                .oauth2Login().and()
                .logout().logoutSuccessHandler(oidcLogoutSuccessHandler());
        return http.build();
    }

    @Bean
    public ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler successHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(registrationRepository);
        successHandler.setPostLogoutRedirectUri("{baseUrl}");
        return successHandler;
    }
}
