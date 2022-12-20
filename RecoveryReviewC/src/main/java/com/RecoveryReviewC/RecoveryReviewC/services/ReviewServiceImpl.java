package com.RecoveryReviewC.RecoveryReviewC.services;

import com.RecoveryReviewC.RecoveryReviewC.model.Review;
import com.RecoveryReviewC.RecoveryReviewC.model.Vote;
import com.RecoveryReviewC.RecoveryReviewC.repository.ProductRepository;
import com.RecoveryReviewC.RecoveryReviewC.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void upVote(Vote vote){
        Review rv = repository.getReviewById(vote.getReviewId());
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
    public String getReviews() throws JsonProcessingException {
        List<Review> reviewList = repository.getAllReviews();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(reviewList);
        System.out.println(" Review:  " + json);
        return json;
    }



}



