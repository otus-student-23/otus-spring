package ru.otus.mar.booklibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin().and()
                .authorizeRequests((authorize) -> authorize
                        .antMatchers("/actuator/hystrix.stream").permitAll()
                        .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        .antMatchers("/swagger-ui/**", "/v3/**", "/actuator/**", "/h2-console/**",
                                "/hystrix/**").hasRole("ADMIN")
                        .antMatchers("/*", "/js/**", "/api/**", "/webjars/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().denyAll()
                )
                .formLogin().loginPage("/login.html").permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
