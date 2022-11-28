package com.Review1_Q.Review1_Q.services;


import com.Review1_Q.Review1_Q.model.RatingFrequency;
import com.Review1_Q.Review1_Q.model.Review;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    Review getReviewById(String reviewId) throws IOException, InterruptedException;

    Review internalGetReviewById(String reviewId);

    List<Review> getAllReviews() throws IOException, InterruptedException;

    List<Review> getAllMyReviews();

    List<Review> getAllReviewsBySku(String sku) throws IOException, InterruptedException;

    List<Review> internalGetAllReviewsBySku(String sku);

    List<Review> getReviewsByProductOrderByVotes(String sku) throws IOException, InterruptedException;

    List<Review> getAllPendingReviews() throws IOException, InterruptedException;

    RatingFrequency getRatingFrequencyOfProduct(String sku) throws IOException, InterruptedException;

    String getStatus(String reviewId) throws IOException, InterruptedException;

}
