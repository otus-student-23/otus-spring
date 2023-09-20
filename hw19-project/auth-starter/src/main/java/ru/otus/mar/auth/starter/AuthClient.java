package ru.otus.mar.auth.starter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@FeignClient(name = "auth-microservice")
public interface AuthClient {

    @GetMapping("/lan/auth/user/{id}/roles")
    String getRoles(@PathVariable UUID id, @RequestParam String username);

    default List<String> getRolesSilently(UUID id, String username) {
        try {
            return Arrays.stream(getRoles(id, username).split(",")).map(String::trim).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
