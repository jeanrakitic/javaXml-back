package com.review.reviewplatform.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean isBusinessOwner; // Field for business ownership

    private String firstName;
    private String lastName;

    private String city;
    private String country;

    @Column(columnDefinition = "boolean default false")
    private boolean isAdmin;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "profile_image_id") // Reference to UserImage table
    private UserImage profileImage;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
