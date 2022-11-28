package com.Review1_Q.Review1_Q.RabbitMQ;

import com.Review1_Q.Review1_Q.model.Review;
import com.Review1_Q.Review1_Q.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    private ReviewRepository reviewRepository;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void consumeJsonMessageToCreateFrom1(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.save(rv);
        System.out.println("Creating review in Database with id:" + rv.getReviewId());
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void consumeJsonMessageToDeleteFrom1(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.delete(rv);
        System.out.println("Deleting review in Database with id:" + rv.getReviewId());
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void consumeJsonMessageToChangeFrom1(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.save(rv);
        System.out.println("Changing review status in Database with id:" + rv.getReviewId());
    }

    @RabbitListener(queues = "#{autoDeleteQueue4.name}")
    public void consumeJsonMessageToCreateFrom2(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.save(rv);
        System.out.println("Creating review in Database with id:" + rv.getReviewId());
    }

    @RabbitListener(queues = "#{autoDeleteQueue5.name}")
    public void consumeJsonMessageToDeleteFrom2(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.delete(rv);
        System.out.println("Deleting review in Database with id:" + rv.getReviewId());
    }

    @RabbitListener(queues = "#{autoDeleteQueue6.name}")
    public void consumeJsonMessageToChangeFrom2(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.save(rv);
        System.out.println("Changing review status in Database with id:" + rv.getReviewId());
    }

}
