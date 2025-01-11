package com.review.reviewplatform.repositories;
import com.review.reviewplatform.models.Business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByNameContainingIgnoreCase(String name);
    List<Business> findByCityAndCountry(String city, String country);
    List<Business> findByIsVerified(boolean isVerified);
    List<Business> findByOwnerId(Long ownerId);
    List<Business> findByCategoriesName(String categoryName);
    Page<Business> findByCity(String city, Pageable pageable);

}


