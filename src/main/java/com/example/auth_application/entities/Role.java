package com.example.auth_application.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity

public class Role {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(unique = true ,nullable = false )
    private String name ;
}
