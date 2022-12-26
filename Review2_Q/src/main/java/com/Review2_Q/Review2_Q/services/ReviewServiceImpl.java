package com.Review2_Q.Review2_Q.services;

import com.Review2_Q.Review2_Q.Interfaces.repository.ReviewRepository;
import com.Review2_Q.Review2_Q.model.Review;
import com.Review2_Q.Review2_Q.model.VoteDTO;
import com.Review2_Q.Review2_Q.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @Override
    public void updateDataBaseReview(String review) throws JsonProcessingException {
        try{
            JSONArray array = new JSONArray(review);

            for(int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);

                ObjectMapper objectMapper = new ObjectMapper();
                Review rv = objectMapper.readValue(jsonObject1.toString(), Review.class);
                System.out.println("RV: " + rv.getReviewId());
                repository.save(rv);
            }

        }catch(Exception e) {
            System.out.println("Error in Result as " + e.toString());
        }
    }

    @Override
    public Review getReviewCreatedByVote(String voteIdIfCreatedFromVote){
        System.out.println(repository.getReviewCreatedByVote(voteIdIfCreatedFromVote));
        if(repository.getReviewCreatedByVote(voteIdIfCreatedFromVote) != null){
            return repository.getReviewCreatedByVote(voteIdIfCreatedFromVote);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no reviews");
        }
    }
}
