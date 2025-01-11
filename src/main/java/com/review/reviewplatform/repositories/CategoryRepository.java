package com.review.reviewplatform.repositories;

import com.review.reviewplatform.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
    Page<Category> findAll(Pageable pageable);

}