package com.review.reviewplatform.services.Interfaces;

import com.review.reviewplatform.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReviewService {
    Review createReview(Review review);
    Review getReviewById(Long id);
    List<Review> getReviewsByBusinessId(Long businessId);
    List<Review> getReviewsByUserId(Long userId);
    Page<Review> getBusinessReviews(Long businessId, Pageable pageable);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
    void flagReview(Long id);
    void voteHelpful(Long id);
    void voteNotHelpful(Long id);
}