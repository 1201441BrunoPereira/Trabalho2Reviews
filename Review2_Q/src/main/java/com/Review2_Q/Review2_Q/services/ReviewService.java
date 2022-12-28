package com.Review2_Q.Review2_Q.services;

import com.Review2_Q.Review2_Q.model.Review;
import com.Review2_Q.Review2_Q.model.VoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;

public interface ReviewService {

    Review getReviewById(String reviewId);

    List<Review> getAllReviews();

    List<Review> getAllMyReviews();

    List<Review> getAllReviewsBySku(String sku);

    List<Review> getReviewsByProductOrderByVotes(String sku);

    List<Review> getAllPendingReviews();

    String getStatus(String reviewId);

    void upVote(VoteDTO vote);

    Review createReviewByOther(String review) throws JsonProcessingException;

    void deleteReviewByOther(String review) throws JsonProcessingException;

    void updateDataBaseReview(String review);

    Review getReviewCreatedByVote(String Optional);
}
