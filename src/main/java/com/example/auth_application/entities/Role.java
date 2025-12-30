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
@Table(name = "role")
public class Role {
    @Id
   private UUID ID = UUID.randomUUID();
    @Column(unique = true ,nullable = false )
    private String name ;
}
