package com.review.reviewplatform.controllers;

import com.review.reviewplatform.models.Review;
import com.review.reviewplatform.models.User;
import com.review.reviewplatform.services.Interfaces.ReviewService;
import com.review.reviewplatform.services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/reviews",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    private UserService userService;


    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        // Check if the user is not a business owner
        User user = userService.getUserById(review.getUser().getId());
        if (user.isBusinessOwner()) {
            return ResponseEntity.badRequest().body(null); // Reject business owners
        }

        return ResponseEntity.ok(reviewService.createReview(review));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<Review>> getReviewsByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(reviewService.getReviewsByBusinessId(businessId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @GetMapping("/business/{businessId}/page")
    public ResponseEntity<Page<Review>> getBusinessReviewsPaged(
            @PathVariable Long businessId,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getBusinessReviews(businessId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,
            @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @PutMapping("/{id}/flag")
    public ResponseEntity<?> flagReview(@PathVariable Long id) {
        reviewService.flagReview(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/helpful")
    public ResponseEntity<?> markHelpful(@PathVariable Long id) {
        reviewService.voteHelpful(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/not-helpful")
    public ResponseEntity<?> markNotHelpful(@PathVariable Long id) {
        reviewService.voteNotHelpful(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("<message>Review deleted successfully.</message>");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                    .body("<error>Review not found with ID: " + id + "</error>");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("<error>Something went wrong. Please try again later.</error>");
        }
    }

}