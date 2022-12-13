package com.Review2_C.Review2_C.services;



import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.model.ReviewDTO;
import com.Review2_C.Review2_C.model.VoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;


public interface ReviewService {


    Review create(ReviewDTO rev) throws IOException, InterruptedException;

    Boolean approveRejectReview(String reviewId, Boolean status) throws IOException, InterruptedException;

    Boolean deleteReview(String reviewId) throws IOException, InterruptedException;

    void addProduct(String sku);

    void upVote(VoteDTO vote);

    Review createReviewByOther(String review) throws JsonProcessingException;

    void deleteReviewByOther(String review) throws JsonProcessingException;
}
