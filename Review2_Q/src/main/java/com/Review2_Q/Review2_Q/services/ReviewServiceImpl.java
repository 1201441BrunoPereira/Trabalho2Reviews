package com.Review2_Q.Review2_Q.services;

import com.Review2_Q.Review2_Q.model.Review;
import com.Review2_Q.Review2_Q.model.VoteDTO;
import com.Review2_Q.Review2_Q.Interfaces.repository.ReviewRepository;
import com.Review2_Q.Review2_Q.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Review getReviewById(String reviewId){
        return repository.getReviewById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found"));
    }


    @Override
    public List<Review> getAllReviewsBySku(String sku){
        return repository.getReviewsByProduct(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
    }


    @Override
    public List<Review> getAllReviews(){
        return repository.getAllReviews().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
    }

    @Override
    public List<Review> getAllPendingReviews(){
        return repository.getAllPendingReviews().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no review pending"));
    }

    @Override
    public List<Review> getAllMyReviews(){
        Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
        return repository.getAllMyReviews(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reviews not found"));
    }

    public List<Review> getReviewsByProductOrderByVotes(String sku){
        return repository.getReviewsOrderByVotes(sku);
    }

    @Override
    public String getStatus(String reviewId){
        Review review = repository.getReviewById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found"));
        return review.getStatus();
    }

    @Override
    public void upVote(VoteDTO vote){
        Review rv = repository.getReview(vote.getReviewId());
        rv.upVote(vote.isVote());
        repository.save(rv);
    }

    @Override
    public Review createReviewByOther(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        return repository.save(rv);
    }

    @Override
    public void deleteReviewByOther(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        repository.delete(rv);
    }

}
