package com.RecoveryReviewQ.RecoveryReviewQ.services;


import com.RecoveryReviewQ.RecoveryReviewQ.model.Review;
import com.RecoveryReviewQ.RecoveryReviewQ.model.Vote;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface ReviewService {


    void upVote(Vote vote);

    Review createReviewByOther(String review) throws JsonProcessingException;

    void deleteReviewByOther(String review) throws JsonProcessingException;

    String getReviews() throws JsonProcessingException;
}
