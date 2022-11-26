package com.example.Review2_C.Review2_C.repository;

import com.example.Review2_C.Review2_C.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId")
    Review getReviewById(@Param("reviewId") String reviewId);

}

