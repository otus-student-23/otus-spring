package ru.otus.mar.auth.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    @Order(99)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf().disable()
                .headers().frameOptions().disable().and()//--- iframe
                .authorizeRequests(request -> request
                        //.antMatchers("/lan/**").hasIpAddress("127.0.0.1/24")//todo hasIpAddress("lan")
                        .antMatchers("/lan/**", "/swagger-ui/**", "/v3/**", "/lk/**", "/actuator/**").permitAll()//todo remove lk, actuator hasIpAddress
                        //.antMatchers("/**").hasAuthority("SCOPE_openid")
                        .antMatchers("/api/**").hasAuthority("user")
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter(AuthClient authClient) {
        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            return realmAccess.get("roles").stream().filter("default-roles-booklibrary"::equals)
                    .flatMap(r -> authClient.getRolesSilently(
                            UUID.fromString(jwt.getSubject()), jwt.getClaim(PREFERRED_USERNAME)).stream())
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
        });
        return jwtAuthConverter;
    }
}
