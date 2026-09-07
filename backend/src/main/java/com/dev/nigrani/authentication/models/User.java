package com.dev.nigrani.authentication.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, unique = true, length = 100)
    private String officialId;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private UserRole role;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @PrePersist
    private void setCreatedAt() { createdAt = LocalDateTime.now(); }
}
