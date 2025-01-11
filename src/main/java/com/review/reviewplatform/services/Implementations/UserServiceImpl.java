package com.review.reviewplatform.services.Implementations;

import com.review.reviewplatform.models.User;
import com.review.reviewplatform.models.UserImage;
import com.review.reviewplatform.repositories.UserImageRepository;
import com.review.reviewplatform.repositories.UserRepository;
import com.review.reviewplatform.services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserImageRepository userImageRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        // Fetch the existing user
        User user = getUserById(id);

        // Update basic details only (no profile image update)
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setCity(userDetails.getCity());
        user.setCountry(userDetails.getCountry());

        // Save updated user details
        return userRepository.save(user);
    }




    @Override
    public User updateProfileImage(Long userId, String imageUrl) {
        User user = getUserById(userId); // Fetch user
        UserImage userImage = user.getProfileImage();

        if (userImage == null) {
            userImage = new UserImage();
            userImage.setUser(user); // Link the user
        }

        // Update image and save
        userImage.setImageUrl(imageUrl);
        userImage = userImageRepository.save(userImage); // Persist UserImage

        // Update user with the saved image
        user.setProfileImage(userImage);
        return userRepository.save(user);
    }


    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
