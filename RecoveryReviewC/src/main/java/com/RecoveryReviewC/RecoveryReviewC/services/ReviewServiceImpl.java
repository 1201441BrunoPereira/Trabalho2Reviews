package com.RecoveryReviewC.RecoveryReviewC.services;

import com.RecoveryReviewC.RecoveryReviewC.model.Review;
import com.RecoveryReviewC.RecoveryReviewC.model.Vote;
import com.RecoveryReviewC.RecoveryReviewC.repository.ProductRepository;
import com.RecoveryReviewC.RecoveryReviewC.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

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
    public String getReviews(String message) throws JsonProcessingException {
        String pageNumber = message.substring(6);
        int page = Integer.parseInt(pageNumber);
        List<Review> reviewList = repository.getAllByPage(PageRequest.of(page,10));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(reviewList);
        System.out.println(" Review:  " + json);
        return json;
    }
}



