package com.RecoveryReviewQ.RecoveryReviewQ.repository;

import com.RecoveryReviewQ.RecoveryReviewQ.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId")
    Review getReview(@Param("reviewId") String reviewId);

    @Query("SELECT r FROM Review r")
    List<Review> getAllByPage(Pageable pageable);

}

