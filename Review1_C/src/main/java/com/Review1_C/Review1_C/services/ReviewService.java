package com.Review1_C.Review1_C.services;

import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.ReviewDTO;
import com.Review1_C.Review1_C.VoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import java.io.IOException;


public interface ReviewService {


    Review create(ReviewDTO rev) throws IOException, InterruptedException;

    Boolean approveRejectReview(String reviewId, Boolean status) throws IOException, InterruptedException;

    Boolean deleteReview(String reviewId) throws IOException, InterruptedException;

    void upVote(VoteDTO vote);

    Review createReviewByOther(String review) throws JsonProcessingException;

    void deleteReviewByOther(String review) throws JsonProcessingException;

    void updateDataBaseReview(String review);

    void createReviewByVote(String vote) throws JsonProcessingException, JSONException;
}
