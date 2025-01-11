package com.review.reviewplatform.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "businesses")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "business_categories",
            joinColumns = @JoinColumn(name = "business_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    private String address;
    private String city;
    private String country;
    private String phone;
    private String website;

    @Column(length = 500)
    private String businessImageUrl;


    @Column(columnDefinition = "boolean default false")
    private boolean isVerified;

    private double averageRating;
    private int totalReviews;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ElementCollection
    @CollectionTable(name = "business_images", joinColumns = @JoinColumn(name = "business_id"))
    @Column(name = "image_url", length = 500)
    private Set<String> imageUrls = new HashSet<>();
}