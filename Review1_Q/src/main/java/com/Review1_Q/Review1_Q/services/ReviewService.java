package com.Review1_Q.Review1_Q.services;


import com.Review1_Q.Review1_Q.model.RatingFrequency;
import com.Review1_Q.Review1_Q.model.Review;
import com.Review1_Q.Review1_Q.model.ReviewDTO;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    Review getReviewById(Long reviewId) throws IOException, InterruptedException;

    List<Review> getAllReviews() throws IOException, InterruptedException;

    List<Review> getAllMyReviews();

    List<Review> getAllReviewsBySku(String sku) throws IOException, InterruptedException;

    List<Review> getReviewsByProductOrderByVotes(String sku) throws IOException, InterruptedException;

    List<Review> getAllPendingReviews() throws IOException, InterruptedException;

    RatingFrequency getRatingFrequencyOfProduct(String sku) throws IOException, InterruptedException;

    String getStatus(Long reviewId) throws IOException, InterruptedException;

}
