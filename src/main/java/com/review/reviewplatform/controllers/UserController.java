package com.review.reviewplatform.controllers;

import com.review.reviewplatform.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.review.reviewplatform.services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.review.reviewplatform.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Check if username or email already exists
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("<message>Username already exists</message>");
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("<message>Email already exists</message>");
        }

        // Create the user
        userService.createUser(user);

        return ResponseEntity.ok("<message>Registration successful</message>");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
    @PatchMapping(value = "/users/{id}/make-business-owner", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> makeUserBusinessOwner(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            user.setBusinessOwner(true); // Set business owner status
            userService.updateUser(id, user); // Save updated user

            return ResponseEntity.ok("<message>User is now a business owner.</message>");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("<error>User not found.</error>");
        }
    }

    @PatchMapping(value = "/users/{id}/make-admin", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> makeUserAdmin(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            user.setAdmin(true); // Set admin status
            userService.updateUser(id, user); // Save updated user

            return ResponseEntity.ok("<message>User is now an admin.</message>");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("<error>User not found.</error>");
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("<message>User deleted successfully.</message>");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                    .body("<error>User not found with ID: " + id + "</error>");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("<error>Something went wrong. Please try again later.</error>");
        }
    }

}