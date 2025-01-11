package com.review.reviewplatform.repositories;
import com.review.reviewplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> findByIsActive(boolean isActive);
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);



}
