package com.Review2_C.Review2_C.services;

import com.Review2_C.Review2_C.Interfaces.RabbitMQ.RabbitMQPublisher;
import com.Review2_C.Review2_C.Interfaces.repository.ProductRepository;
import com.Review2_C.Review2_C.Interfaces.repository.ReviewRepository;
import com.Review2_C.Review2_C.VoteDTO;
import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.model.ReviewDTO;
import com.Review2_C.Review2_C.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

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

    @Autowired
    private EmailConfigImpl emailConfig;

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

    @Override
    public void updateDataBaseReview(String review) {
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
    public void createReviewByVote(String vote) throws JsonProcessingException, JSONException {
        JSONObject object = new JSONObject(vote);
        Review rv = Review.readJson(vote);
        if(productRepository.getProductDTOBySku(object.getString("sku")) !=null) {
            jsonProducer.sendJsonMessageToCreate(rv);
            repository.save(rv);
            emailConfig.sendSimpleMail("1201441@isep.ipp.pt", "One review has been created with: \nid: "+rv.getReviewId()+"\ntext: "+rv.getText(), "Created Review From Vote");

        }else{
            jsonProducer.sendJsonMessageToDeleteTempVote(rv.getVoteIdIfCreatedFromVote());
        }
    }

}



