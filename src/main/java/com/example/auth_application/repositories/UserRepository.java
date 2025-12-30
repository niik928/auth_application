package com.example.auth_application.repositories;

import com.example.auth_application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

 Optional<User> findByEmail(String email);

 boolean existsByEmail(String email);
}
