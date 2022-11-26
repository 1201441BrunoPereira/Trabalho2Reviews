package com.example.Review2_C.Review2_C.services;



import com.example.Review2_C.Review2_C.model.Review;
import com.example.Review2_C.Review2_C.model.ReviewDTO;

import java.io.IOException;


public interface ReviewService {


    Review create(ReviewDTO rev) throws IOException, InterruptedException;

    Boolean approveRejectReview(String reviewId, Boolean status) throws IOException, InterruptedException;

    Boolean deleteReview(String reviewId) throws IOException, InterruptedException;

}
