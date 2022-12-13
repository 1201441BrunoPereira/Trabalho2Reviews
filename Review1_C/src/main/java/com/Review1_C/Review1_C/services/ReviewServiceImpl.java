package com.Review1_C.Review1_C.services;

import com.Review1_C.Review1_C.Interfaces.RabbitMQ.RabbitMQPublisher;
import com.Review1_C.Review1_C.model.ProductDTO;
import com.Review1_C.Review1_C.model.Review;
import com.Review1_C.Review1_C.model.ReviewDTO;
import com.Review1_C.Review1_C.model.VoteDTO;
import com.Review1_C.Review1_C.Interfaces.repository.ProductRepository;
import com.Review1_C.Review1_C.Interfaces.repository.ReviewRepository;
import com.Review1_C.Review1_C.security.JwtUtils;
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
    private ProductRepository productRepository;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RabbitMQPublisher jsonProducer;

    @Override
    public Review create(ReviewDTO rev) throws JsonProcessingException {
        if(productRepository.getProductDTOBySku(rev.getSku()) !=null){
            try {
                Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
                final Review obj = Review.newFrom(rev,userId);
                jsonProducer.sendJsonMessageToCreate(obj);
                repository.save(obj);
                return obj;
            }catch (IllegalArgumentException e){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "You are not logged");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product doesn't exist");
        }
    }

    @Override
    public Boolean approveRejectReview(String reviewId, Boolean status) throws JsonProcessingException {
        Review review = repository.getReviewById(reviewId);
        try {
            if (Objects.equals(review.getStatus(), "PENDING")) {
                if (status) {
                    review.setStatus("APPROVED");
                } else {
                    review.setStatus("REJECTED");
                }
                jsonProducer.sendJsonMessageToChangeStatus(review);
                repository.save(review);
                return true;
            }else {
                return false;
            }
        }catch (NullPointerException e) {
            return false;
        }
    }

    public Boolean deleteReview(String reviewId) throws JsonProcessingException {

        Long userId = Long.valueOf(jwtUtils.getUserFromJwtToken(jwtUtils.getJwt()));
        Review review = repository.getReviewById(reviewId);
        if(review == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review doesn't exist");
        }
        if (review.getDownVote() == 0 && review.getUpVote() ==0 && Objects.equals(review.getUserId(), userId)) {
            jsonProducer.sendJsonMessageToDelete(review);
            repository.delete(review);
            return true;
        }else
            return false;
    }

    @Override
    public void addProduct(String sku){
        ProductDTO productDTO = new ProductDTO(sku);
        productRepository.save(productDTO);
    }

    @Override
    public void upVote(VoteDTO vote){
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



}



