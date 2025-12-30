package com.example.auth_application.dtos;

import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Data
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private UUID id;
    private String email;
    private String name;
    private String image;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}

