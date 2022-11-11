package com.Review1_C.Review1_C.services;

import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.ReviewDTO;
import com.Review1_C.Review1_C.repository.ProductRepository;
import com.Review1_C.Review1_C.repository.Review2Repository;
import com.Review1_C.Review1_C.repository.ReviewRepository;
import com.Review1_C.Review1_C.repository.VoteRepository;
import com.Review1_C.Review1_C.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private Review2Repository repository2;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Review create(ReviewDTO rev) throws IOException, InterruptedException {
        boolean isPresent = productRepository.isPresent(rev.getSku());
        if(isPresent){
            Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
            final Review obj = Review.newFrom(rev,userId);
            return repository.save(obj);
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product doesn't exist");
        }

    }

    @Override
    public Boolean approveRejectReview(Long reviewId, Boolean status){
        Review review = repository.getReviewById(reviewId);
        try {
            if (Objects.equals(review.getStatus(), "PENDING")) {
                if (status) {
                    review.setStatus("APPROVED");
                } else {
                    review.setStatus("REJECTED");
                }
                repository.save(review);
                return true;
            }else {
                return false;
            }
        }catch (NullPointerException e){
            return false;
        }

    }

    public Boolean deleteReview(Long reviewId) throws IOException, InterruptedException {

        var votes = voteRepository.getVotesByReviewId(reviewId);
        Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
        Review review = repository.getReviewById(reviewId);
        if (votes == 0 && Objects.equals(review.getUserId(), userId)) {
            repository.delete(review);
            return true;
        }else
            return false;
    }

    @Override
    public String getStatus(Long reviewId) throws IOException, InterruptedException {
        Review review = repository.getReviewById(reviewId);
        if(review == null){
           review = repository2.getReviewbyId(reviewId);
        }
        return review.getStatus();
    }

}
