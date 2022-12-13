package com.Review1_C.Review1_C.Interfaces.repository;

import com.Review1_C.Review1_C.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId")
    Review getReviewById(@Param("reviewId") String reviewId);

}

