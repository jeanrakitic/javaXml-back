package com.review.reviewplatform.services.Interfaces;

import com.review.reviewplatform.models.User;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User updateUser(Long id, User user);

    User updateProfileImage(Long userId, String imageUrl);

    void deleteUser(Long id);
}