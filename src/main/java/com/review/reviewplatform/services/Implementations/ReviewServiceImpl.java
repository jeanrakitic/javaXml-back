package com.review.reviewplatform.services.Implementations;

import com.review.reviewplatform.models.Review;
import com.review.reviewplatform.repositories.ReviewRepository;
import com.review.reviewplatform.services.Interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public List<Review> getReviewsByBusinessId(Long businessId) {
        return reviewRepository.findByBusinessId(businessId);
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Page<Review> getBusinessReviews(Long businessId, Pageable pageable) {
        return reviewRepository.findByBusinessId(businessId, pageable);
    }

    @Override
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = getReviewById(id);
        review.setTitle(reviewDetails.getTitle());
        review.setContent(reviewDetails.getContent());
        review.setRating(reviewDetails.getRating());
        review.setReviewImageUrl(reviewDetails.getReviewImageUrl());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }

    @Override
    public void flagReview(Long id) {
        Review review = getReviewById(id);
        review.setFlagged(true);
        reviewRepository.save(review);
    }

    @Override
    public void voteHelpful(Long id) {
        Review review = getReviewById(id);
        review.setHelpfulVotes(review.getHelpfulVotes() + 1);
        reviewRepository.save(review);
    }

    @Override
    public void voteNotHelpful(Long id) {
        Review review = getReviewById(id);
        review.setNotHelpfulVotes(review.getNotHelpfulVotes() + 1);
        reviewRepository.save(review);
    }
}