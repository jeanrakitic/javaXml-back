package com.review.reviewplatform.repositories;

import com.review.reviewplatform.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBusinessId(Long businessId);
    List<Review> findByUserId(Long userId);
    Page<Review> findByBusinessId(Long businessId, Pageable pageable);
    List<Review> findByRatingGreaterThanEqual(int rating);
    List<Review> findByIsFlagged(boolean isFlagged);
}