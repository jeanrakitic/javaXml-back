package com.review.reviewplatform.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_images")
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for each image

    @Column(nullable = false, length = 500)
    private String imageUrl; // Stores the URL of the image

    @OneToOne
    @JoinColumn(name = "user_id") // Foreign key to associate with a user
    private User user;
}
