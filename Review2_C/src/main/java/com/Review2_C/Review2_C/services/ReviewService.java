package com.Review2_C.Review2_C.services;



import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.model.ReviewDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;


public interface ReviewService {


    Review create(ReviewDTO rev) throws JsonProcessingException;

    Boolean approveRejectReview(String reviewId, Boolean status);

    Boolean deleteReview(String reviewId) throws IOException, InterruptedException;

    void addProduct(String sku);

}
