package com.Review2_C.Review2_C.services;


import com.Review2_C.Review2_C.VoteDTO;
import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.model.ReviewDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;


public interface ReviewService {


    Review create(ReviewDTO rev) throws JsonProcessingException;

    Boolean approveRejectReview(String reviewId, Boolean status) throws JsonProcessingException;

    Boolean deleteReview(String reviewId) throws JsonProcessingException;

    void upVote(VoteDTO vote);

    Review createReviewByOther(String review) throws JsonProcessingException;

    void deleteReviewByOther(String review) throws JsonProcessingException;

    void updateDataBaseReview(String review);

    void createReviewByVote(String vote) throws JsonProcessingException, JSONException;
}
