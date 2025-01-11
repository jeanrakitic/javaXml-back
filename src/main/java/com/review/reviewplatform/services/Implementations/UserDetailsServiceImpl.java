package com.review.reviewplatform.services.Implementations;

import com.review.reviewplatform.models.User;
import com.review.reviewplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username or throw exception
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return a Spring Security User object
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.isAdmin() ? "ADMIN" : "USER") // Assign roles
                .disabled(!user.isActive()) // Handle active/inactive users
                .build();
    }
}
