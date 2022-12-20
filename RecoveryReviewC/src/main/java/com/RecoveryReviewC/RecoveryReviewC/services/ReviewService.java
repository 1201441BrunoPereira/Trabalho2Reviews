package com.RecoveryReviewC.RecoveryReviewC.services;


import com.RecoveryReviewC.RecoveryReviewC.model.Review;
import com.RecoveryReviewC.RecoveryReviewC.model.Vote;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;


public interface ReviewService {


    void upVote(Vote vote);

    Review createReviewByOther(String review) throws JsonProcessingException;

    void deleteReviewByOther(String review) throws JsonProcessingException;

    String getReviews() throws JsonProcessingException;
}
