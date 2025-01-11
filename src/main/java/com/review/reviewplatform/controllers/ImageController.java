package com.review.reviewplatform.controllers;

import com.review.reviewplatform.models.UserImage;
import com.review.reviewplatform.repositories.UserImageRepository;
import com.review.reviewplatform.services.Interfaces.FirebaseStorageService;
import com.review.reviewplatform.services.Interfaces.UserService;
import com.review.reviewplatform.services.Interfaces.BusinessService;
import com.review.reviewplatform.services.Interfaces.ReviewService;

import com.review.reviewplatform.models.Business;
import com.review.reviewplatform.models.User;
import com.review.reviewplatform.models.Review;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired private FirebaseStorageService firebaseStorageService;
    @Autowired private UserService userService;
    @Autowired private BusinessService businessService;
    @Autowired private ReviewService reviewService;
    @Autowired private UserImageRepository userImageRepository;

    // ========== REUSABLE METHOD ==========
    private Set<String> processAndUploadImages(MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No files provided");
        }

        Set<String> imageUrls = new HashSet<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("One or more files are empty");
            }
            String imageUrl = firebaseStorageService.uploadFile(file);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    // ========== USER PROFILE IMAGE ==========
    @Transactional
    @PostMapping(value = "/users/{userId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // Validate file
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("<error>File is missing or empty.</error>");
            }

            // Upload image to Firebase
            String imageUrl = firebaseStorageService.uploadFile(file);

            // Update user profile image via service
            userService.updateProfileImage(userId, imageUrl);

            return ResponseEntity.ok("<message>Profile image uploaded successfully.</message>");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("<error>Failed to upload image.</error>");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("<error>User not found.</error>");
        }
    }

    



    // ========== BUSINESS IMAGES ==========
    @PostMapping(value = "/businesses/{businessId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadBusinessImages(
            @PathVariable Long businessId,
            @RequestParam("files") MultipartFile[] files,
            HttpServletRequest request) {

        try {
            System.out.println("Content-Type: " + request.getContentType());

            Set<String> imageUrls = processAndUploadImages(files);

            Business business = businessService.getBusinessById(businessId);
            business.getImageUrls().addAll(imageUrls);
            businessService.updateBusiness(businessId, business);

            return ResponseEntity.ok(imageUrls);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("<error>" + e.getMessage() + "</error>");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("<error>File upload failed.</error>");
        }
    }

    // ========== REVIEW IMAGES ==========
    @PostMapping(value = "/reviews/{reviewId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadReviewImages(
            @PathVariable Long reviewId,
            @RequestParam("files") MultipartFile[] files,
            HttpServletRequest request) {

        try {
            System.out.println("Content-Type: " + request.getContentType());

            Set<String> imageUrls = processAndUploadImages(files);

            Review review = reviewService.getReviewById(reviewId);
            review.getImageUrls().addAll(imageUrls);
            reviewService.updateReview(reviewId, review);

            return ResponseEntity.ok(imageUrls);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("<error>" + e.getMessage() + "</error>");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("<error>File upload failed.</error>");
        }
    }
}
