package com.Review2_Q.Review2_Q.Interfaces.repository;

import com.Review2_Q.Review2_Q.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId")
    Optional<Review> getReviewById(@Param("reviewId") String reviewId);

    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId")
    Review getReview(@Param("reviewId") String reviewId);

    @Query("SELECT r FROM Review r")
    Optional<List<Review>> getAllReviews();

    @Query("SELECT r FROM Review r WHERE r.skuProduct = :skuProduct")
    Optional<List<Review>> getReviewsByProduct(@Param("skuProduct") String skuProduct);

    @Query("SELECT r FROM Review r WHERE r.status ='PENDING'")
    Optional<List<Review>> getAllPendingReviews();

    @Query("SELECT r FROM Review r WHERE r.userId = :userId")
    Optional<List<Review>> getAllMyReviews(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE r.skuProduct = :sku and r.status = 'APPROVED' ORDER BY r.upVote desc")
    List <Review> getReviewsOrderByVotes(@Param("sku") String sku);

}

