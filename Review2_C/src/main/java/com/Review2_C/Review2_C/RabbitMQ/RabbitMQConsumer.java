package com.Review2_C.Review2_C.RabbitMQ;


import com.Review2_C.Review2_C.model.Review;
import com.Review2_C.Review2_C.repository.ReviewRepository;
import com.Review2_C.Review2_C.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void consumeJsonMessageToCreate(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.save(rv);
        System.out.println(review);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void consumeJsonMessageToDelete(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.delete(rv);
        System.out.println(review);
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void consumeJsonMessageToChange(String review) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Review rv = objectMapper.readValue(review, Review.class);
        reviewRepository.save(rv);
        System.out.println(review);
    }

    @RabbitListener(queues =  "#{autoDeleteQueue4.name}")
    public void consumeJsonMessageToCreateProduct(String sku){
        reviewService.addProduct(sku);
        System.out.println(sku);
    }

}
