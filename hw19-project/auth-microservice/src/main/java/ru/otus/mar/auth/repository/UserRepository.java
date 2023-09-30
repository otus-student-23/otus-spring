package ru.otus.mar.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mar.auth.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}