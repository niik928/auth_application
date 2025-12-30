package com.example.auth_application.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",columnDefinition = "BINARY(16)")
    private UUID ID;
    @Column(name = "email", unique = true, length = 300)
    private String email;
    private String name;
    private String password;
    private String image;
    private boolean enable=true;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private Provider provider = Provider.LOCAL ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;


    @PrePersist
    protected void onCreate(){   ///run before save into database

        Instant now = Instant.now();
        if(createdAt == null) createdAt = now;
        updatedAt = now;
    }
@PreUpdate
    protected void onUpdate(){
        updatedAt = Instant.now();
    }


}
